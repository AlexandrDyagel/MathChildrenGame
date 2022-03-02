package com.xelar.legayd.myfirstgit.domain

import androidx.lifecycle.LiveData

class GetCategoryCongratulationListUseCase(private val repository: CongratulationRepository) {

    fun getCategoryCongratulations(): LiveData<List<CategoryCongratulation>>{
        return repository.getCategoryCongratulations()
    }
}