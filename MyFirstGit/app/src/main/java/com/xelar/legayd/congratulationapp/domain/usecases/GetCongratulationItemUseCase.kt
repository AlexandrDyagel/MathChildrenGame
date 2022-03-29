package com.xelar.legayd.congratulationapp.domain.usecases

import androidx.lifecycle.LiveData
import com.xelar.legayd.congratulationapp.domain.entities.Congratulation
import com.xelar.legayd.congratulationapp.domain.repositories.CongratulationRepository

class GetCongratulationItemUseCase(private val repository: CongratulationRepository) {

    fun getCongratulationItem(id: Int): LiveData<Congratulation> {
        return repository.getCongratulationItem(id)
    }

    operator fun invoke(id: Int): LiveData<Congratulation>{
        return repository.getCongratulationItem(id)
    }
}