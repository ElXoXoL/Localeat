package com.bite.bite.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.bite.bite.utils.TypefaceUtils

class ExtraTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    init {
        typeface = TypefaceUtils.extraBold
    }

}