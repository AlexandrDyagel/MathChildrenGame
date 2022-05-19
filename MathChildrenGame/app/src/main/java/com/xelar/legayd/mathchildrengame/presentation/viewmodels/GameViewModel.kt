package com.xelar.legayd.mathchildrengame.presentation.viewmodels

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.data.GameRepositoryImpl
import com.xelar.legayd.mathchildrengame.domain.models.GameResult
import com.xelar.legayd.mathchildrengame.domain.models.GameSettings
import com.xelar.legayd.mathchildrengame.domain.models.Level
import com.xelar.legayd.mathchildrengame.domain.models.Question
import com.xelar.legayd.mathchildrengame.domain.usecases.GenerateQuestionUseCase
import com.xelar.legayd.mathchildrengame.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private var timer: CountDownTimer? = null

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val context = application
    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    // Правильный ли был ответ
    private val _checkRightAnswers = MutableLiveData<Boolean>()
    val checkRightAnswers: LiveData<Boolean>
        get() = _checkRightAnswers

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    fun startGame(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun startGame(gameCustomSettings: GameCustomSettings) {
        this.gameSettings = mapCustomSettingsToGameSettings(gameCustomSettings)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
        if (gameSettings.gameTimeInSeconds == 0){
            _formattedTime.value = "Бесконечная игра"
        }
        if (gameSettings.gameTimeInSeconds > 0){
            startTimer()
        }
        generateQuestion()
        updateProgress()
    }

    private fun mapCustomSettingsToGameSettings(gameCustomSettings: GameCustomSettings): GameSettings {
        return GameSettings(
            maxSumValue = gameCustomSettings.maxSumValue,
            minCountOfRightAnswers = gameCustomSettings.minCountOfRightAnswers,
            minPercentOfRightAnswers = gameCustomSettings.minPercentOfRightAnswers,
            gameTimeInSeconds = gameCustomSettings.gameTimeInSeconds,
            levelReward = gameCustomSettings.levelReward
        )
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCountOfRightAnswers.value =
            countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
            _checkRightAnswers.value = true
        } else {
            _checkRightAnswers.value = false
        }
        countOfQuestions++
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format(
            "%02d:%02d",
            minutes,
            leftSeconds
        ) // если число меньше двух цифр, то оно будет дополненно нулями
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCountOfRightAnswers.value == true && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}