package com.xelar.legayd.congratulationapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xelar.legayd.congratulationapp.domain.entities.CategoryCongratulation
import com.xelar.legayd.congratulationapp.domain.entities.Congratulation
import com.xelar.legayd.congratulationapp.domain.repositories.CongratulationRepository

object CongratulationRepositoryImpl : CongratulationRepository {

    private val listCongratulation = MutableLiveData<List<Congratulation>>()
    private val listCategoryCongratulation = MutableLiveData<List<CategoryCongratulation>>()
    private val itemCongratulation = MutableLiveData<Congratulation>()

    init {
        val list = arrayListOf<Congratulation>()
        val listCategory = arrayListOf<CategoryCongratulation>()
        for (i in 0..20) {
            list.add(
                Congratulation(
                    i,
                    "Поздравление $i\n" +
                            "Поздравление $i\n" +
                            "Поздравление $i\n" +
                            "\n" +
                            "Поздравление $i\n" +
                            "Поздравление $i\n" +
                            "Поздравление $i\n" +
                            "\n" +
                            "Поздравление $i\n" +
                            "Поздравление $i\n" +
                            "Поздравление $i",
                    i
                )
            )
        }
        listCategory.add(CategoryCongratulation(0, "С Днем рождения"))
        listCategory.add(CategoryCongratulation(1, "С Днем ВДВ"))
        listCategory.add(CategoryCongratulation(2, "С Новым годом"))
        listCategory.add(CategoryCongratulation(3, "любимым"))
        listCategory.add(CategoryCongratulation(4, "с юбилеем"))
        listCategory.add(CategoryCongratulation(5, "годовщина свадьбы"))
        listCategory.add(CategoryCongratulation(6, "женщине"))
        listCategory.add(CategoryCongratulation(7, "брату"))
        listCategory.add(CategoryCongratulation(8, "подруге"))
        listCategory.add(CategoryCongratulation(9, "сестре"))
        listCategory.add(CategoryCongratulation(10, "племяннице"))
        listCategory.add(CategoryCongratulation(11, "племяннику"))
        listCategory.add(CategoryCongratulation(12, "дедушке"))
        listCategory.add(CategoryCongratulation(13, "бабуле"))
        listCategory.add(CategoryCongratulation(14, "парню"))
        listCategory.add(CategoryCongratulation(15, "папе"))
        listCategory.add(CategoryCongratulation(16, "с пополнением"))
        listCategory.add(CategoryCongratulation(17, "старушке"))
        listCategory.add(CategoryCongratulation(18, "мамульке"))
        listCategory.add(CategoryCongratulation(19, "сестренке"))

        listCongratulation.postValue(list)
        listCategoryCongratulation.postValue(listCategory)
    }

    override fun getCongratulations(): LiveData<List<Congratulation>> {
        return listCongratulation
    }

    override fun getCategoryCongratulations(): LiveData<List<CategoryCongratulation>> {
        return listCategoryCongratulation
    }

    override fun getCongratulationItem(id: Int): LiveData<Congratulation> {
        itemCongratulation.value = listCongratulation.value?.find { it.id == id }
        return itemCongratulation
    }

    override fun editCongratulationItem(congratulation: Congratulation) {
        TODO("Not yet implemented")
    }
}