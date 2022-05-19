package com.xelar.legayd.mathchildrengame.presentation.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings

class CustomSettingsItemDiffCallback: DiffUtil.ItemCallback<GameCustomSettings>() {
    override fun areItemsTheSame(
        oldItem: GameCustomSettings,
        newItem: GameCustomSettings
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: GameCustomSettings,
        newItem: GameCustomSettings
    ): Boolean = oldItem == newItem
}