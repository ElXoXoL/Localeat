package com.bite.bite.ui.contacts

import androidx.recyclerview.widget.LinearLayoutManager
import com.bite.bite.R
import com.bite.bite.application.base.BaseActivity
import com.bite.bite.application.extensions.load
import com.bite.bite.application.extensions.setAlwaysExpanded
import com.bite.bite.application.extensions.setBackgroundBlack
import com.bite.bite.application.extensions.toast
import com.bite.bite.models.Contact
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_contacts.*

class DialogContacts(
    private val activity: BaseActivity,
    private val list: MutableList<Contact>
) {
    private var dialog: BottomSheetDialog? = null

    init {
        createDialog()
        showDialog()
    }

    private fun createDialog() {
        dialog = BottomSheetDialog(activity, R.style.SheetDialog)
        with(dialog!!){
            setContentView(R.layout.dialog_contacts)
            setCancelable(true)
            setBackgroundBlack()

            img_contacts_phone.load(R.drawable.ic_phone)
            rec_contacts.layoutManager = LinearLayoutManager(activity)
            val adapterContacts = AdapterContacts{}

            rec_contacts.adapter = adapterContacts

            list.forEach {
                adapterContacts.addElem(it)
            }
        }
    }

    private fun showDialog() {
        dialog?.show()
    }

    private fun cancel(){
        dialog?.dismiss()
    }
}