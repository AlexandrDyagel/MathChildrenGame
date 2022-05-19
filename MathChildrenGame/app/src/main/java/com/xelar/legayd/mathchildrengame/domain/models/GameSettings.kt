package com.xelar.legayd.mathchildrengame.domain.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue: Int = 0, // максимальное значение суммы
    val minCountOfRightAnswers: Int = 0, // минимальное значение правильных ответов до победы
    val minPercentOfRightAnswers: Int = 0, // минимальный процент правильных ответов до победы
    val gameTimeInSeconds: Int = 0, // продолжительность игры
    val levelReward: Int = 0 // награда за прохождение
): Parcelable