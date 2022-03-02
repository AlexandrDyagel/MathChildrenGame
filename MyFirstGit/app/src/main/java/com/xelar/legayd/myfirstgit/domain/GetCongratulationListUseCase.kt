package com.xelar.legayd.myfirstgit.domain

import androidx.lifecycle.LiveData

class GetCongratulationListUseCase(private val repository: CongratulationRepository) {

    fun getCongratulations(): LiveData<List<Congratulation>>{
        return repository.getCongratulations()
    }
}