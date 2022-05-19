package com.xelar.legayd.mathchildrengame.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.xelar.legayd.mathchildrengame.R

class ColorDropDownAdapter(val context: Context, private var listColor: ArrayList<Int>): BaseAdapter(), Filterable {

    override fun getCount(): Int {
        return listColor.size
    }

    override fun getItem(i: Int): Any {
        return listColor[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(
            R.layout.list_color_item,
            viewGroup,
            false
        )
        val viewHolder = ViewHold(view)
        viewHolder.bind(listColor[i])
        return view
    }

    inner class ViewHold(view: View):RecyclerView.ViewHolder(view){
        private val lineColor = view.findViewById<TextView>(R.id.lineColor)
        fun bind(color: Int){
            //lineColor.setBackgroundColor(ContextCompat.getColor(context,color))
            lineColor.setBackgroundColor(color)
        }
    }

    override fun getFilter(): Filter {
        val filter = object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResult = FilterResults()
                val list = arrayListOf<Int>()
                if (constraint == null || constraint.isEmpty()){
                    list.addAll(listColor)
                } else {
                    val color = constraint.toString().toInt()
                    for (item in listColor){
                        if (item == color) {
                            list.add(item)
                        }
                    }
                }
                filterResult.values = list
                filterResult.count = list.size
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listColor.clear()
                if (results?.values is List<*>) {
                    val list = results.values as List<*>
                    for (i in list){
                        i.toString().toInt()
                        listColor.add(i.toString().toInt())
                    }
                }
                notifyDataSetChanged()
            }

        }
        return filter
    }
}