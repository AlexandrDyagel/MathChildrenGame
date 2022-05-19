package com.xelar.legayd.mathchildrengame.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "game_custom_settings")
data class GameCustomSettings(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String, // название кнопки
    val colorBg: Int, // цвет кнопки
    val maxSumValue: Int, // максимальное значение суммы
    val minCountOfRightAnswers: Int, // минимальное значение правильных ответов до победы
    val minPercentOfRightAnswers: Int, // минимальный процент правильных ответов до победы
    val gameTimeInSeconds: Int, // продолжительность игры
    val levelReward: Int = 0// награда за прохождение
) : Parcelable