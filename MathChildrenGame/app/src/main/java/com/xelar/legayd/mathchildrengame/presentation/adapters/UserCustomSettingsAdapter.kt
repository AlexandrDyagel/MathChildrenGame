package com.xelar.legayd.mathchildrengame.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.presentation.diffutils.CustomSettingsItemDiffCallback
import com.xelar.legayd.mathchildrengame.presentation.viewholders.CustomSettingsViewHolder

class UserCustomSettingsAdapter : ListAdapter<GameCustomSettings, CustomSettingsViewHolder>(
    CustomSettingsItemDiffCallback()
) {
    var onCustomSettingsClickListener: ((GameCustomSettings) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomSettingsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_settings_item, parent, false)
        return CustomSettingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomSettingsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.buttonCustomSettings.setOnClickListener {
            onCustomSettingsClickListener?.invoke(item)
        }
    }
}