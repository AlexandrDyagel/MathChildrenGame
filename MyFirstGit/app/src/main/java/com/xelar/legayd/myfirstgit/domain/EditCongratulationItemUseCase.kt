package com.xelar.legayd.myfirstgit.domain

class EditCongratulationItemUseCase(private val repository: CongratulationRepository) {

    fun editCongratulationItem(congratulation: Congratulation){
        repository.editCongratulationItem(congratulation)
    }
}