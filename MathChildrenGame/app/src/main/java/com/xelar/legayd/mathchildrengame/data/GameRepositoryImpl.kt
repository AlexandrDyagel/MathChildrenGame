package com.xelar.legayd.mathchildrengame.data

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import androidx.core.content.res.getDrawableOrThrow
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.domain.models.*
import com.xelar.legayd.mathchildrengame.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }

        return Question(
            sum = sum,
            visibleNumber = visibleNumber,
            options = options.shuffled().toList()
        )
    }

    override fun getStatusSettings(context: Context): List<StatusSettings> {
        val statusIcons = getStatusIcons()
        val statusStrings = context.resources.getStringArray(R.array.statuses)
        val statusSettings = mutableListOf<StatusSettings>()
        if (statusStrings.isNotEmpty()) {
            for (i in statusStrings.indices) {
                val status = StatusSettings(
                    statusName = statusStrings[i],
                    totalRatingsStatus = 0,
                    //totalRatingNextStatus = i * i * 30 + i * (i + 50) + 50,
                    totalRatingNextStatus = i*10 + 50,
                    statusIcons[i]
                )
                statusSettings.add(status)
            }
        }
        return statusSettings
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> GameSettings(
                10, // максимальное значение суммы
                1, // минимальное значение правильных ответов до победы
                50, // минимальный процент правильных ответов до победы
                4, // продолжительность игры
                45 // // награда за прохождение
            )
            Level.VERY_EASY -> GameSettings(
                8,
                10,
                60,
                70,
                1
            )
            Level.EASY -> GameSettings(
                10,
                10,
                70,
                60,
                2
            )
            Level.NORMAL -> GameSettings(
                20,
                20,
                80,
                60,
                4
            )
            Level.HARD -> GameSettings(
                30,
                30,
                90,
                60,
                6
            )
            Level.VERY_HARD -> GameSettings(
                30,
                30,
                90,
                50,
                10
            )
            Level.CUSTOM -> GameSettings()
        }
    }

    private fun getStatusIcons(): List<Int>{
        return listOf(
            R.drawable.newstatus1,
            R.drawable.newstatus2,
            R.drawable.newstatus3,
            R.drawable.newstatus4,
            R.drawable.newstatus5,
            R.drawable.newstatus6,
            R.drawable.newstatus7,
            R.drawable.newstatus8,
            R.drawable.newstatus9,
            R.drawable.newstatus10
        )
    }
}