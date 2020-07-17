package com.bite.bite.views

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatTextView
import com.bite.bite.utils.TypefaceUtils

class MediumEditText(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatEditText(context, attrs) {

    init {
        typeface = TypefaceUtils.medium
    }

}