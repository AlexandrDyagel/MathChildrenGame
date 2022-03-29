package com.xelar.legayd.congratulationapp.domain.usecases

import androidx.lifecycle.LiveData
import com.xelar.legayd.congratulationapp.domain.entities.Congratulation
import com.xelar.legayd.congratulationapp.domain.repositories.CongratulationRepository

class GetCongratulationListUseCase(
    private val repository: CongratulationRepository
    ) {

    operator fun invoke(): LiveData<List<Congratulation>>{
        return repository.getCongratulations()
    }

}