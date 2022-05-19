package com.xelar.legayd.mathchildrengame.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.data.database.AppDatabase
import com.xelar.legayd.mathchildrengame.domain.models.UserInfo
import com.xelar.legayd.mathchildrengame.domain.repository.LocalRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LocalRepositoryImpl(application: Application) : LocalRepository {

    private val compositeDisposable = CompositeDisposable()
    private val db = AppDatabase.getInstance(application)

    override fun getUserInfo(): LiveData<UserInfo> {
        return db.userInfoDao().getUserInfo()
    }

    override fun insertUserInfo(userInfo: UserInfo) {
        val disposable = db.userInfoDao().insertUserInfo(userInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("userInfo", "insert userInfo complete")
            }
        compositeDisposable.add(disposable)
    }

    override fun insertCustomSettings(gameCustomSettings: GameCustomSettings) {
        val disposable = db.gameSettingsDao().insertSettings(gameCustomSettings)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("userInfo", "inserted gameSettings complete")
            }
        compositeDisposable.add(disposable)
    }

    override fun deleteCustomSettings(gameCustomSettings: GameCustomSettings) {
        val disposable = db.gameSettingsDao().deleteSettings(gameCustomSettings)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("userInfo", "deleted gameSettings complete")
            }
        compositeDisposable.add(disposable)
    }

    override fun getCustomSettings(): LiveData<List<GameCustomSettings>> {
        return db.gameSettingsDao().getSettings()
    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }
}
