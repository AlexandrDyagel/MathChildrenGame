package com.xelar.legayd.myfirstgit.domain

import androidx.lifecycle.LiveData

class GetCongratulationItemUseCase(private val repository: CongratulationRepository) {

    fun getCongratulationItem(id: Int): LiveData<Congratulation> {
        return repository.getCongratulationItem(id)
    }
}