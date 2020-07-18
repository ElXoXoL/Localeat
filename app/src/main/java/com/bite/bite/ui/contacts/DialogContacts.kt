package com.bite.bite.ui.contacts

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.bite.bite.R
import com.bite.bite.application.base.BaseActivity
import com.bite.bite.application.extensions.*
import com.bite.bite.models.Contact
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_contacts.*
import java.lang.Exception


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
//            setAlwaysExpanded()

            img_contacts_phone.load(R.drawable.ic_phone)

            val tvViews = listOf(
                tv_contact_1,
                tv_contact_2,
                tv_contact_3
            )

            for (i in tvViews.indices){
                val tv = tvViews[i]
                try {
                    val phone = list[i].value
                    tv.visibility = View.VISIBLE
                    tv.text = phone
                    tv.setOnClickListener {
                        call(phone)
                    }
                    tv.setOnLongClickListener {
                        activity.copyToClipboard(phone)
                        context.getString(R.string.text_copied_phone).toast()
                        return@setOnLongClickListener true
                    }
                } catch (e: Exception){}
            }
        }
    }

    private fun call(phone: String){
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        activity.startActivity(intent)
    }

    private fun showDialog() {
        dialog?.show()
    }

    private fun cancel(){
        dialog?.dismiss()
    }
}