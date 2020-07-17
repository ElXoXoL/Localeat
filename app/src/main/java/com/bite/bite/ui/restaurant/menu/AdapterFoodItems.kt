package com.bite.bite.ui.restaurant.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bite.bite.R
import com.bite.bite.application.base.BaseAdapter
import com.bite.bite.application.base.BaseViewHolder
import com.bite.bite.application.extensions.color
import com.bite.bite.application.extensions.load
import com.bite.bite.application.extensions.signed
import com.bite.bite.models.FoodItem
import kotlinx.android.synthetic.main.menu_elem.view.*

class AdapterFoodItems: BaseAdapter<FoodItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.menu_elem, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<FoodItem>, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(view : View) : BaseViewHolder<FoodItem>(view) {

        override fun bind(item: FoodItem) {

            itemView.tv_elem_name.text = item.name
            itemView.tv_elem_descr.text = item.descr
            itemView.tv_elem_price.text = item.price.toString().signed

        }
    }

}