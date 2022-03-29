package com.xelar.legayd.congratulationapp.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xelar.legayd.congratulationapp.R
import com.xelar.legayd.congratulationapp.databinding.CongratulationItemBinding
import com.xelar.legayd.congratulationapp.domain.entities.Congratulation
import com.xelar.legayd.congratulationapp.presentation.diffutils.CongratulationItemDiffCallback

class CongratulationAdapter : ListAdapter<Congratulation, RecyclerView.ViewHolder>(
    CongratulationItemDiffCallback()
) {

    var onCongratulationClickListener: ((Congratulation) -> Unit)? = null

    private var count = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = when (viewType) {
            CONGRAT_VIEW -> R.layout.congratulation_item
            ADS_VIEW -> R.layout.ads_item
            else ->
                throw RuntimeException(
                    "Произошла ошибка макета списка " +
                            "элементов viewType = $viewType"
                )
        }
        Log.d("ADS", "CreateViewHolder count = ${++count}")
        return when (viewType) {
            CONGRAT_VIEW ->
                CongratulationViewHolder(inflater.inflate(layout, parent, false))
            else -> AdsViewHolder(inflater.inflate(layout, parent, false))
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is CongratulationViewHolder -> {
                val congratulation = getItem(position)
                with(holder) {
                    bind(congratulation)
                    itemView.setOnClickListener {
                        onCongratulationClickListener?.invoke(congratulation)
                    }
                }
            }
        }
    }

    inner class CongratulationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CongratulationItemBinding.bind(itemView)

        fun bind(congratulation: Congratulation) = with(binding) {
            tvMessage.text = congratulation.message
        }
    }

    inner class AdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }


    override fun getItemViewType(position: Int): Int {
        return if (position != 0 && position % INTERVAL_POSITION_ADS == 0) {
            Log.d("ADS", "На позиции $position сработала реклама")
            ADS_VIEW
        } else {
            Log.d("ADS", "На позиции $position сработало поздравление")
            CONGRAT_VIEW
        }
    }

    companion object {
        const val CONGRAT_VIEW = 0
        const val ADS_VIEW = 1

        const val INTERVAL_POSITION_ADS = 6
    }
}