package com.xelar.legayd.congratulationapp.domain.repositories

import androidx.lifecycle.LiveData
import com.xelar.legayd.congratulationapp.domain.entities.CategoryCongratulation
import com.xelar.legayd.congratulationapp.domain.entities.Congratulation

interface CongratulationRepository {

    fun getCongratulations(): LiveData<List<Congratulation>>

    fun getCategoryCongratulations(): LiveData<List<CategoryCongratulation>>

    fun getCongratulationItem(id: Int): LiveData<Congratulation>

    fun editCongratulationItem(congratulation: Congratulation)

}