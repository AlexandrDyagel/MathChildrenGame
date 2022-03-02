package com.xelar.legayd.myfirstgit.presentation.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.xelar.legayd.myfirstgit.domain.Congratulation

class CongratulationItemDiffCallback: DiffUtil.ItemCallback<Congratulation>() {
    override fun areItemsTheSame(oldItem: Congratulation, newItem: Congratulation) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Congratulation, newItem: Congratulation) =
        oldItem == newItem
}