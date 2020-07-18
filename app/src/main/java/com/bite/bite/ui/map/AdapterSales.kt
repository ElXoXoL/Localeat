package com.bite.bite.ui.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bite.bite.R
import com.bite.bite.application.base.BaseAdapter
import com.bite.bite.application.base.BaseViewHolder
import com.bite.bite.application.extensions.color
import com.bite.bite.application.extensions.load
import com.bite.bite.models.RestaurantObj
import com.bite.bite.models.Sales
import kotlinx.android.synthetic.main.sales_item.view.*

class AdapterSales(val itemClick: (pos: Int) -> (Unit)): BaseAdapter<RestaurantObj>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.sales_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view : View) : BaseViewHolder<RestaurantObj>(view) {

        override fun bind(item: RestaurantObj) {

            itemView.img_sales_photo.load(item.restaurant.img)
            itemView.tv_sales_name.text = item.restaurant.name

            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<RestaurantObj>, position: Int) {
        holder.bind(list[position])
    }

}