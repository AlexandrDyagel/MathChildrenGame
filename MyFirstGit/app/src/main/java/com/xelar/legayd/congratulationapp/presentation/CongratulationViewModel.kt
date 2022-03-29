package com.xelar.legayd.congratulationapp.presentation

import androidx.lifecycle.ViewModel
import com.xelar.legayd.congratulationapp.data.CongratulationRepositoryImpl
import com.xelar.legayd.congratulationapp.domain.usecases.GetCategoryCongratulationListUseCase
import com.xelar.legayd.congratulationapp.domain.usecases.GetCongratulationListUseCase

class CongratulationViewModel : ViewModel() {


    private val repository = CongratulationRepositoryImpl

    private val getCongratulationList = GetCongratulationListUseCase(repository)
    private val getCategoryCongratulationList = GetCategoryCongratulationListUseCase(repository)

    val listCongratulation = getCongratulationList()
    val listCategoryCongratulation = getCategoryCongratulationList()

}