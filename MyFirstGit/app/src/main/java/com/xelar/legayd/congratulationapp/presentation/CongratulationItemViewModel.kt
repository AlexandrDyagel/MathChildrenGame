package com.xelar.legayd.congratulationapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xelar.legayd.congratulationapp.data.CongratulationRepositoryImpl
import com.xelar.legayd.congratulationapp.domain.entities.Congratulation
import com.xelar.legayd.congratulationapp.domain.usecases.GetCongratulationItemUseCase

class CongratulationItemViewModel: ViewModel() {

    private val repository = CongratulationRepositoryImpl

    private val getCongratulationItem = GetCongratulationItemUseCase(repository)
    //private val editCongratulationItem = EditCongratulationItemUseCase(repository)

    fun getCongratItem(id: Int): LiveData<Congratulation> {
        return getCongratulationItem(id)
    }

}