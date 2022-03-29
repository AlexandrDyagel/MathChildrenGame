package com.xelar.legayd.congratulationapp.domain.usecases

import androidx.lifecycle.LiveData
import com.xelar.legayd.congratulationapp.domain.entities.CategoryCongratulation
import com.xelar.legayd.congratulationapp.domain.repositories.CongratulationRepository

class GetCategoryCongratulationListUseCase(private val repository: CongratulationRepository) {

    operator fun invoke(): LiveData<List<CategoryCongratulation>>{
        return repository.getCategoryCongratulations()
    }
}