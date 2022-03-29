package com.xelar.legayd.congratulationapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xelar.legayd.congratulationapp.R
import com.xelar.legayd.congratulationapp.databinding.CategoryItemBinding
import com.xelar.legayd.congratulationapp.domain.entities.CategoryCongratulation

class CategoryCongratulationAdapter:
    RecyclerView.Adapter<CategoryCongratulationAdapter.CategoryViewHolder>() {

    var listCategoryCongratulation = listOf<CategoryCongratulation>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    var onCategoryClickListener: ((CategoryCongratulation) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryCongratulationAdapter.CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CategoryCongratulationAdapter.CategoryViewHolder,
        position: Int
    ) {
        val categoryCongratulation = listCategoryCongratulation[position]
        with(holder) {
            bindCategory(categoryCongratulation)
            itemView.setOnClickListener {
                onCategoryClickListener?.invoke(categoryCongratulation)
            }
        }

    }

    override fun getItemCount() = listCategoryCongratulation.size

    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = CategoryItemBinding.bind(itemView)

        fun bindCategory(category: CategoryCongratulation) = with(binding){
            chipCategoryCongratulation.text = category.name
        }
    }
}