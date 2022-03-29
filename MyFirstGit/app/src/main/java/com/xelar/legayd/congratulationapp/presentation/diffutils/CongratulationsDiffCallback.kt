package com.xelar.legayd.congratulationapp.presentation.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.xelar.legayd.congratulationapp.domain.entities.Congratulation

class CongratulationsDiffCallback(
    private val oldList: List<Congratulation>,
    private val newList: List<Congratulation>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}