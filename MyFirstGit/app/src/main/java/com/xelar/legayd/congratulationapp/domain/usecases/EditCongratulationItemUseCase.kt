package com.xelar.legayd.congratulationapp.domain.usecases

import com.xelar.legayd.congratulationapp.domain.entities.Congratulation
import com.xelar.legayd.congratulationapp.domain.repositories.CongratulationRepository

class EditCongratulationItemUseCase(private val repository: CongratulationRepository) {

    fun editCongratulationItem(congratulation: Congratulation){
        repository.editCongratulationItem(congratulation)
    }
}