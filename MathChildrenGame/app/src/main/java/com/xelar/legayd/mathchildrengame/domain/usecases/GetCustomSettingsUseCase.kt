package com.xelar.legayd.mathchildrengame.domain.usecases

import androidx.lifecycle.LiveData
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.domain.repository.LocalRepository

class GetCustomSettingsUseCase(private val repository: LocalRepository) {

    operator fun invoke(): LiveData<List<GameCustomSettings>>{
        return repository.getCustomSettings()
    }

}