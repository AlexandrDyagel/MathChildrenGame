package com.xelar.legayd.myfirstgit.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xelar.legayd.myfirstgit.data.CongratulationRepositoryImpl
import com.xelar.legayd.myfirstgit.domain.Congratulation
import com.xelar.legayd.myfirstgit.domain.GetCategoryCongratulationListUseCase
import com.xelar.legayd.myfirstgit.domain.GetCongratulationListUseCase

class CongratulationViewModel : ViewModel() {

    private val repository = CongratulationRepositoryImpl

    private val getCongratulationList = GetCongratulationListUseCase(repository)
    private val getCategoryCongratulationList = GetCategoryCongratulationListUseCase(repository)

    val listCongratulation = getCongratulationList.getCongratulations()
    val listCategoryCongratulation = getCategoryCongratulationList.getCategoryCongratulations()

}