package com.xelar.legayd.mathchildrengame.domain.usecases

import android.content.Context
import com.xelar.legayd.mathchildrengame.domain.models.StatusSettings
import com.xelar.legayd.mathchildrengame.domain.repository.GameRepository

class GetStatusSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(context: Context): List<StatusSettings>{
        return repository.getStatusSettings(context)
    }
}