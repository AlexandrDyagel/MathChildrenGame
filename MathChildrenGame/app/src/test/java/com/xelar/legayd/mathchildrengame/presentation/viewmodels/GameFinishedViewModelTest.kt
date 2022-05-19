package com.xelar.legayd.mathchildrengame.presentation.viewmodels

import android.app.Application
import com.xelar.legayd.mathchildrengame.domain.models.GameResult
import com.xelar.legayd.mathchildrengame.domain.models.GameSettings
import com.xelar.legayd.mathchildrengame.domain.models.StatusSettings
import org.junit.Assert
import org.junit.Test

class GameFinishedViewModelTest {

    @Test
    fun gettingNewStatus() {
        val gameFinishedViewModel = GameFinishedViewModel(application = Application())
        val gameResult = GameResult(
            winner = true,
            countOfRightAnswers = 10,
            countOfQuestions = 13,
            GameSettings(
                10,
                10,
                70,
                60,
                12
            )
        )
        val oldStatusSettings = StatusSettings(
            "Новичок",
            23,
            50,
            5
        )
        val actual = gameFinishedViewModel.newStatusSettings(oldStatusSettings, gameResult)
        val expected = StatusSettings(
            "Новичок",
            35,
            50,
            5
        )
        Assert.assertEquals(expected,actual)
    }
}