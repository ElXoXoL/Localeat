package com.bite.bite.ui.contacts

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
import com.bite.bite.models.Contact
import com.bite.bite.utils.AnimationUtils
import kotlinx.android.synthetic.main.contacts_item.view.*

class AdapterContacts(val itemClick: (pos: Int) -> (Unit)): BaseAdapter<Contact>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contacts_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view : View) : BaseViewHolder<Contact>(view) {

        override fun bind(item: Contact) {

            itemView.tv_contact.text = item.value

            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

     
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Contact>, position: Int) {
        holder.bind(list[position])
    }

}