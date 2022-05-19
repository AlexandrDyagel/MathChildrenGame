package com.xelar.legayd.mathchildrengame.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xelar.legayd.mathchildrengame.data.LocalRepositoryImpl
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.domain.usecases.GetCustomSettingsUseCase
import com.xelar.legayd.mathchildrengame.domain.usecases.InsertCustomSettingsUseCase

class CustomSettingsViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val MAX_VALUE_TIME_GAME = 3600
    }

    private val repository = LocalRepositoryImpl(application)
    private val insertCustomSettingsUseCase = InsertCustomSettingsUseCase(repository)
    private val getCustomSettingsUseCase = GetCustomSettingsUseCase(repository)

    val gameSettings = getCustomSettingsUseCase()

    private val _errorInputTitle = MutableLiveData<Boolean>()
    val errorInputTitle: LiveData<Boolean>
        get() = _errorInputTitle

    private val _errorInputMaxSumValue = MutableLiveData<Boolean>()
    val errorInputMaxSumValue: LiveData<Boolean>
        get() = _errorInputMaxSumValue

    private val _errorInputMaxSumValueMin = MutableLiveData<Boolean>()
    val errorInputMaxSumValueMin: LiveData<Boolean>
        get() = _errorInputMaxSumValueMin

    private val _errorInputMinCountOfRightAnswers = MutableLiveData<Boolean>()
    val errorInputMinCountOfRightAnswers: LiveData<Boolean>
        get() = _errorInputMinCountOfRightAnswers

    private val _errorInputMinPercentOfRightAnswers = MutableLiveData<Boolean>()
    val errorInputMinPercentOfRightAnswers: LiveData<Boolean>
        get() = _errorInputMinPercentOfRightAnswers

    private val _errorInputGameTimeInSeconds = MutableLiveData<Boolean>()
    val errorInputGameTimeInSeconds: LiveData<Boolean>
        get() = _errorInputGameTimeInSeconds

    private val _errorInputGameTimeInSecondsMaxValue = MutableLiveData<Boolean>()
    val errorInputGameTimeInSecondsMaxValue: LiveData<Boolean>
        get() = _errorInputGameTimeInSecondsMaxValue

    private val _validInputFields = MutableLiveData<Boolean>()
    val validInputFields: LiveData<Boolean>
        get() = _validInputFields

    fun setDataFields(
        _isChecked: Boolean,
        _title: String?,
        _colorBg: Int,
        _maxSumValue: String?,
        _minCountOfRightAnswers: String?,
        _minPercentOfRightAnswers: String?,
        _gameTimeInSeconds: String?
    ) {
        val title = parseTitle(_title)
        val maxSumValue = parseInteger(_maxSumValue)
        val minCountOfRightAnswers = parseInteger(_minCountOfRightAnswers)
        val minPercentOfRightAnswers = parseInteger(_minPercentOfRightAnswers)
        val gameTimeInSeconds =
            if (_isChecked) {
                0
            } else {
                parseInteger(_gameTimeInSeconds)
            }


        val fieldsValidate = validateInput(
            title,
            maxSumValue,
            minCountOfRightAnswers,
            minPercentOfRightAnswers,
            gameTimeInSeconds
        )

        Log.d("userInfo", "valid = $fieldsValidate")
        if (fieldsValidate) {
            val customSettings = GameCustomSettings(
                title = title,
                colorBg = _colorBg,
                maxSumValue = maxSumValue,
                minCountOfRightAnswers = minCountOfRightAnswers,
                minPercentOfRightAnswers = minPercentOfRightAnswers,
                gameTimeInSeconds = gameTimeInSeconds
            )
            Log.d("userInfo", "settings = $customSettings")
            insertSettings(customSettings)
            _validInputFields.value = true
        }
    }

    private fun insertSettings(gameCustomSettings: GameCustomSettings) {
        insertCustomSettingsUseCase(gameCustomSettings)
    }

    private fun parseTitle(text: String?): String {
        return text?.trim() ?: ""
    }

    private fun parseInteger(text: String?): Int {
        return try {
            text?.trim()?.toInt() ?: -1
        } catch (ex: Exception) {
            -1
        }
    }

    private fun validateInput(
        title: String,
        maxSumValue: Int,
        minCountOfRightAnswers: Int,
        minPercentOfRightAnswers: Int,
        gameTimeInSeconds: Int
    ): Boolean {
        var result = true
        if (title.isBlank()) {
            _errorInputTitle.value = true
            result = false
        }
        if (maxSumValue <= 0) {
            _errorInputMaxSumValue.value = true
            result = false
        } else if (maxSumValue < 7){
            _errorInputMaxSumValueMin.value = true
            result = false
        }

        if (minCountOfRightAnswers <= 0) {
            _errorInputMinCountOfRightAnswers.value = true
            result = false
        }
        if (minPercentOfRightAnswers <= 0) {
            _errorInputMinPercentOfRightAnswers.value = true
            result = false
        }
        if (gameTimeInSeconds <= -1) {
            _errorInputGameTimeInSeconds.value = true
            result = false
        }
        if (gameTimeInSeconds > MAX_VALUE_TIME_GAME) {
            _errorInputGameTimeInSecondsMaxValue.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputTitle() {
        _errorInputTitle.value = false
    }

    fun resetErrorInputMaxSumValue() {
        _errorInputMaxSumValue.value = false
    }

    fun resetErrorInputMaxSumValueMin() {
        _errorInputMaxSumValueMin.value = false
    }

    fun resetErrorInputMinCountOfRightAnswers() {
        _errorInputMinCountOfRightAnswers.value = false
    }

    fun resetErrorInputMinPercentOfRightAnswers() {
        _errorInputMinPercentOfRightAnswers.value = false
    }

    fun resetErrorInputGameTimeInSeconds() {
        _errorInputGameTimeInSeconds.value = false
    }

    fun resetErrorInputGameTimeInSecondsMaxValue() {
        _errorInputGameTimeInSecondsMaxValue.value = false
    }

    fun resetInputValidFields() {
        _validInputFields.value = false
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearDisposable()
    }
}
