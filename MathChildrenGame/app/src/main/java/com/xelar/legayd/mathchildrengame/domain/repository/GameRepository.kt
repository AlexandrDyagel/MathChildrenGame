package com.xelar.legayd.mathchildrengame.domain.repository

import android.content.Context
import com.xelar.legayd.mathchildrengame.domain.models.*

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings

    fun getStatusSettings(context: Context): List<StatusSettings>
}