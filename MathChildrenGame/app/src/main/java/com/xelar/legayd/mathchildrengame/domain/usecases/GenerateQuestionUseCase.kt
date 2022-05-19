package com.xelar.legayd.mathchildrengame.domain.usecases

import com.xelar.legayd.mathchildrengame.domain.models.Question
import com.xelar.legayd.mathchildrengame.domain.repository.GameRepository

class GenerateQuestionUseCase(private val gameRepository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question{
        return gameRepository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object{
        private const val COUNT_OF_OPTIONS = 6
    }
}