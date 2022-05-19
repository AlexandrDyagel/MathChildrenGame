package com.xelar.legayd.mathchildrengame.domain.usecases

import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.domain.repository.LocalRepository

class InsertCustomSettingsUseCase(private val repository: LocalRepository) {

    operator fun invoke(gameCustomSettings: GameCustomSettings){
        repository.insertCustomSettings(gameCustomSettings)
    }

}