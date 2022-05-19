package com.xelar.legayd.mathchildrengame.presentation.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.databinding.CustomSettingsItemBinding

class CustomSettingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = CustomSettingsItemBinding.bind(view)

    fun bind(gameCustomSettings: GameCustomSettings) {
        with(binding) {
            val context = root.context
            tvTitle.text = gameCustomSettings.title
            tvMaxSumValue.text = String.format(
                context.resources.getString(R.string.tv_max_sum_value),
                gameCustomSettings.maxSumValue
            )
            tvMinCountOfRightAnswers.text = String.format(
                context.resources.getString(R.string.tv_min_count_of_right_answers),
                gameCustomSettings.minCountOfRightAnswers
            )
            tvMinPercentOfRightAnswers.text = String.format(
                context.resources.getString(R.string.tv_min_percent_of_right_answers),
                gameCustomSettings.minPercentOfRightAnswers
            )
            tvGameTimeInSeconds.text = String.format(
                context.resources.getString(R.string.tv_game_time_in_seconds),
                if (gameCustomSettings.gameTimeInSeconds == 0){
                    context.resources.getString(R.string.infinity_game_time)
                } else {
                    gameCustomSettings.gameTimeInSeconds
                }
            )
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {*/
            /*buttonCustomSettings.backgroundTintList = context.resources.getColorStateList(
                R.color.teal_200,
                context.theme
            )*/

            buttonCustomSettings.setBackgroundColor(gameCustomSettings.colorBg)
            /*}*/
        }
    }
}