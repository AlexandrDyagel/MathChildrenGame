package com.xelar.legayd.myfirstgit.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xelar.legayd.myfirstgit.data.CongratulationRepositoryImpl
import com.xelar.legayd.myfirstgit.domain.Congratulation
import com.xelar.legayd.myfirstgit.domain.EditCongratulationItemUseCase
import com.xelar.legayd.myfirstgit.domain.GetCongratulationItemUseCase

class CongratulationItemViewModel: ViewModel() {

    private val repository = CongratulationRepositoryImpl

    private val getCongratulationItem = GetCongratulationItemUseCase(repository)
    //private val editCongratulationItem = EditCongratulationItemUseCase(repository)

    fun getCongratulationItem(id: Int): LiveData<Congratulation> {
        return getCongratulationItem.getCongratulationItem(id)
    }

}