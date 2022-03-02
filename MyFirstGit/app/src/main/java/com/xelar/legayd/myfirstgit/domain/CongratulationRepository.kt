package com.xelar.legayd.myfirstgit.domain

import androidx.lifecycle.LiveData

interface CongratulationRepository {

    fun getCongratulations(): LiveData<List<Congratulation>>

    fun getCategoryCongratulations(): LiveData<List<CategoryCongratulation>>

    fun getCongratulationItem(id: Int): LiveData<Congratulation>

    fun editCongratulationItem(congratulation: Congratulation)

}