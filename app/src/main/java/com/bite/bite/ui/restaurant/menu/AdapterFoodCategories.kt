package com.bite.bite.ui.restaurant.menu

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
import com.bite.bite.models.FoodType
import kotlinx.android.synthetic.main.menu_category_elem.view.*

class AdapterFoodCategories(val itemClick: (typeId: Int) -> (Unit)): BaseAdapter<FoodType>() {

    var selectedPos = 0
    var lastSelectedView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.menu_category_elem, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<FoodType>, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(view : View) : BaseViewHolder<FoodType>(view) {

        override fun bind(item: FoodType) {

            itemView.tv_item_name.text = item.type
            itemView.img_item_photo.load(item.img)

            setBackground(selectedPos == adapterPosition)

            // Selects first element for the first time
            if (lastSelectedView == null)
                lastSelectedView = itemView.view_menu_category_selected

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
                lastSelectedView = itemView.view_menu_category_selected
            }

            if (isSelected){
                itemView.view_menu_category_selected.appear(500)
            }
        }
    }

}