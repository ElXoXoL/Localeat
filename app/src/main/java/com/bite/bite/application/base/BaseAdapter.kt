package com.bite.bite.application.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bite.bite.R
import com.bite.bite.models.Restaurant
import com.bite.bite.utils.Logger

abstract class BaseAdapter<T>: RecyclerView.Adapter<BaseViewHolder<T>>() {

    var list = mutableListOf<T>()

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }

    fun addElem(item: T){
        list.add(item)
        notifyItemInserted(list.size - 1)
    }

}

abstract class BaseViewHolder<T>(view : View) : RecyclerView.ViewHolder(view) {

    open fun bind(item: T) {

    }
}