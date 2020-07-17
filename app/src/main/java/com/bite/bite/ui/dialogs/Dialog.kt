package com.bite.bite.ui.dialogs

import com.bite.bite.R
import com.bite.bite.application.base.BaseActivity
import com.bite.bite.application.extensions.setAlwaysExpanded
import com.bite.bite.application.extensions.setBackgroundBlack
import com.google.android.material.bottomsheet.BottomSheetDialog

class Dialog(
    private val activity: BaseActivity
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
            setAlwaysExpanded()

        }
    }

    private fun showDialog() {
        dialog?.show()
    }

    private fun cancel(){
        dialog?.dismiss()
    }
}