package com.bite.bite.ui.list

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bite.bite.R
import com.bite.bite.application.base.BaseAdapter
import com.bite.bite.application.base.BaseViewHolder
import com.bite.bite.application.extensions.color
import com.bite.bite.application.extensions.load
import com.bite.bite.application.extensions.loadBitmap
import com.bite.bite.application.extensions.loadNoResize
import com.bite.bite.application.runOnMain
import com.bite.bite.application.runOnWorker
import com.bite.bite.models.Restaurant
import com.bite.bite.models.RestaurantObj
import com.bite.bite.models.TransitionElem
import com.bite.bite.ui.MainViewModel
import kotlinx.android.synthetic.main.restaurant_list_elem.view.*

class AdapterRestaurants(val itemClick: (pos: Int) -> (Unit)): BaseAdapter<RestaurantObj>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list_elem, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<RestaurantObj>, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(view : View) : BaseViewHolder<RestaurantObj>(view) {

        override fun bind(item: RestaurantObj) {

//            runOnWorker {
//                item.bitmap = item.bitmap ?: loadBitmap(item.restaurant.img)
//                runOnMain {
//                    itemView.img_item_photo.load(item.bitmap)
//                }
//            }

            itemView.img_item_photo.load(item.restaurant.img)

            itemView.tv_item_name.text = item.restaurant.name

            itemView.setOnClickListener { itemClick(adapterPosition) }
        }
    }

}