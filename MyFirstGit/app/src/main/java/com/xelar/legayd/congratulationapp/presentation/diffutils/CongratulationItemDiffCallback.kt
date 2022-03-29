package com.xelar.legayd.congratulationapp.presentation.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.xelar.legayd.congratulationapp.domain.entities.Congratulation

class CongratulationItemDiffCallback: DiffUtil.ItemCallback<Congratulation>() {
    override fun areItemsTheSame(oldItem: Congratulation, newItem: Congratulation) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Congratulation, newItem: Congratulation) =
        oldItem == newItem
}