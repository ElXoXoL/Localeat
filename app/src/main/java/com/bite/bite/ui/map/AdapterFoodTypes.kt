package com.bite.bite.ui.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bite.bite.R
import com.bite.bite.application.base.BaseAdapter
import com.bite.bite.application.base.BaseViewHolder
import com.bite.bite.application.extensions.appear
import com.bite.bite.application.extensions.color
import com.bite.bite.application.extensions.disappear
import com.bite.bite.application.extensions.load
import com.bite.bite.models.ColorSet
import com.bite.bite.models.FoodType
import com.bite.bite.utils.AnimationUtils
import kotlinx.android.synthetic.main.food_type_elem.view.*

class AdapterFoodTypes(val itemClick: (pos: Int) -> (Unit)): BaseAdapter<FoodType>() {

    private var selectedPos = 0
    private var lastSelectedView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.food_type_elem, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view : View) : BaseViewHolder<FoodType>(view) {

        override fun bind(item: FoodType) {

            itemView.tv_type_name.text = item.type
            itemView.tv_type_name_selected.text = item.type

            setBackground(selectedPos == adapterPosition)

            // Selects first element for the first time
            if (lastSelectedView == null)
                lastSelectedView = itemView.view_food_type_selected

            itemView.setOnClickListener {
                setBackground(selectedPos != adapterPosition, adapterPosition)

                itemClick(adapterPosition)
            }
        }

        private fun setBackground(isSelected: Boolean, pos: Int? = null){
            if (pos != null && selectedPos != -1 && selectedPos != pos){
                lastSelectedView?.disappear(500)
            }

            if (pos != null) {
                selectedPos = pos
                lastSelectedView = itemView.view_food_type_selected
            }

            if (isSelected){
                itemView.view_food_type_selected.appear(500)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<FoodType>, position: Int) {
        holder.bind(list[position])
    }

}