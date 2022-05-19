package com.xelar.legayd.mathchildrengame.domain.usecases

import com.xelar.legayd.mathchildrengame.domain.models.GameSettings
import com.xelar.legayd.mathchildrengame.domain.models.Level
import com.xelar.legayd.mathchildrengame.domain.repository.GameRepository

class GetGameSettingsUseCase(private val gameRepository: GameRepository) {

    operator fun invoke(level: Level): GameSettings{
        return gameRepository.getGameSettings(level)
    }
}