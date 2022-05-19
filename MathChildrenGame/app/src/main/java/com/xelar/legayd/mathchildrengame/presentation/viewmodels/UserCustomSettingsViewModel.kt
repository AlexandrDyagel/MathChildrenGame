package com.xelar.legayd.mathchildrengame.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.data.LocalRepositoryImpl
import com.xelar.legayd.mathchildrengame.domain.usecases.DeleteCustomSettingsUseCase
import com.xelar.legayd.mathchildrengame.domain.usecases.GetCustomSettingsUseCase

class UserCustomSettingsViewModel(application: Application): AndroidViewModel(application) {

    private val repository = LocalRepositoryImpl(application)
    private val getCustomSettingsUseCase = GetCustomSettingsUseCase(repository)
    private val deleteCustomSettingsUseCase = DeleteCustomSettingsUseCase(repository)

    val customSettings = getCustomSettingsUseCase()

    fun deleteCustomSettings(gameCustomSettings: GameCustomSettings){
        deleteCustomSettingsUseCase(gameCustomSettings)
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearDisposable()
    }
}