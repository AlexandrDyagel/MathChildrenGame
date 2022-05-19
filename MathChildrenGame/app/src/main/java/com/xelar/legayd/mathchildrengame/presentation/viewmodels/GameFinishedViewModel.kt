package com.xelar.legayd.mathchildrengame.presentation.viewmodels

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xelar.legayd.mathchildrengame.data.GameRepositoryImpl
import com.xelar.legayd.mathchildrengame.data.LocalRepositoryImpl
import com.xelar.legayd.mathchildrengame.data.database.AppDatabase
import com.xelar.legayd.mathchildrengame.domain.models.GameResult
import com.xelar.legayd.mathchildrengame.domain.models.StatusSettings
import com.xelar.legayd.mathchildrengame.domain.models.UserInfo
import com.xelar.legayd.mathchildrengame.domain.usecases.GetStatusSettingsUseCase
import com.xelar.legayd.mathchildrengame.domain.usecases.GetUserInfoUserCase
import com.xelar.legayd.mathchildrengame.domain.usecases.InsertUserInfoUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class GameFinishedViewModel(application: Application) : AndroidViewModel(application) {

    private val localRepository = LocalRepositoryImpl(application)
    private val gameRepository = GameRepositoryImpl
    private val context = application
    private val db = AppDatabase.getInstance(context)
    private val getUserInfoUseCase = GetUserInfoUserCase(localRepository)
    private val insertUserInfoUseCase = InsertUserInfoUseCase(localRepository)
    private val getStatusSettings = GetStatusSettingsUseCase(gameRepository)
    private var timer: CountDownTimer? = null

    val userInfo = getUserInfoUseCase()

    private val _progressBarExp = MutableLiveData<Double>()
    val progressBarExp: LiveData<Double>
        get() = _progressBarExp

    private val _activeButtonRetry = MutableLiveData<Boolean>()
    val activeButtonRetry: LiveData<Boolean>
        get() = _activeButtonRetry

    private val _tickTimerButtonRetry = MutableLiveData<Int>()
    val tickTimerButtonRetry: LiveData<Int>
        get() = _tickTimerButtonRetry

    private fun insertUserInfo(userInfo: UserInfo) {
        insertUserInfoUseCase(userInfo)
    }

    fun setGameResult(gameResult: GameResult) {
        startTimerBeforeActiveButtonRetry()
        db.userInfoDao().getUserInfoFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("userInfo", "RxJava userInfo = $it")
                newUserInfo(gameResult.winner, gameResult, it)
            }, {
                Log.d("userInfo", "RxJava Ошибка = ${it.message}")

                val statusSettingsDefault = getStatusSettings(context)[0]
                if (!gameResult.winner) {
                    insertUserInfo(
                        UserInfo(
                            1, 0, gameResult.countOfRightAnswers,
                            statusSettingsDefault
                        )
                    )
                } else {
                    newUserInfo(
                        gameResult.winner, gameResult,
                        UserInfo(1, 0, 0, statusSettingsDefault)
                    )
                }
            })
    }

    private fun newUserInfo(win: Boolean, gameResult: GameResult, userInfo: UserInfo) {
        if (win) {
            userInfo.let {
                var rating = it.rating
                var countOfRightAnswers = it.countOfRightAnswers
                rating += gameResult.gameSettings.levelReward
                countOfRightAnswers += gameResult.countOfRightAnswers

                val statusSettings = newStatusSettings(it.statusSettings, gameResult)

                val newUserInfo = UserInfo(
                    uid = 1,
                    rating = rating,
                    countOfRightAnswers = countOfRightAnswers,
                    statusSettings = statusSettings
                )
                calculateProgressBarExp(statusSettings)
                insertUserInfo(newUserInfo)
                Log.d("userInfo", "new userInfo - > $newUserInfo")
            }
        } else {
            calculateProgressBarExp(userInfo.statusSettings)
        }
    }

    private fun newStatusSettings(
        oldStatusSettings: StatusSettings,
        gameResult: GameResult
    ): StatusSettings {
        val resultTotalRating =
            oldStatusSettings.totalRatingsStatus + gameResult.gameSettings.levelReward

        if (resultTotalRating >= oldStatusSettings.totalRatingNextStatus) {
            val statusName = oldStatusSettings.statusName
            val settings = getStatusSettings(context)

            val temp: StatusSettings? = settings.find { it.statusName == statusName }
            var position = settings.indexOf(temp)
            val nextStatusSettings: StatusSettings
            return if (position < settings.size - 1) {
                nextStatusSettings = settings[++position]
                val name = nextStatusSettings.statusName
                val countNext = nextStatusSettings.totalRatingNextStatus
                val count = resultTotalRating - oldStatusSettings.totalRatingNextStatus
                StatusSettings(
                    statusName = name,
                    totalRatingsStatus = count,
                    totalRatingNextStatus = countNext,
                    statusImage = nextStatusSettings.statusImage
                )
            } else {
                // Последний статус в списке статусов
                StatusSettings(
                    statusName = statusName,
                    totalRatingsStatus = oldStatusSettings.totalRatingNextStatus,
                    totalRatingNextStatus = oldStatusSettings.totalRatingNextStatus,
                    statusImage = oldStatusSettings.statusImage
                )
            }
        } else {
            return StatusSettings(
                statusName = oldStatusSettings.statusName,
                totalRatingsStatus = resultTotalRating,
                totalRatingNextStatus = oldStatusSettings.totalRatingNextStatus,
                statusImage = oldStatusSettings.statusImage
            )
        }
    }

    private fun calculateProgressBarExp(statusSettings: StatusSettings) {
        _progressBarExp.value = statusSettings.totalRatingsStatus /
                statusSettings.totalRatingNextStatus.toDouble() * 100
    }

    private fun startTimerBeforeActiveButtonRetry() {
        _activeButtonRetry.value = false
        var tick = MILLIS_TO_SECONDS.toInt()
        _tickTimerButtonRetry.value = tick

        timer = object : CountDownTimer(
            MILLIS_BEFORE_ACTIVE_BUTTON_RETRY,
            MILLIS_TICK_INTERVAL
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _tickTimerButtonRetry.value = tick--
            }

            override fun onFinish() {
                _activeButtonRetry.value = true
            }
        }
        timer?.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
        localRepository.clearDisposable()
    }

    companion object {
        private const val MILLIS_BEFORE_ACTIVE_BUTTON_RETRY = 3000L
        private const val MILLIS_TICK_INTERVAL = 1000L
        private const val MILLIS_TO_SECONDS = MILLIS_BEFORE_ACTIVE_BUTTON_RETRY / 1000
    }
}

