package com.bite.bite.utils

import android.graphics.Typeface
import com.bite.bite.application.BiteApp

object TypefaceUtils {
    private const val MEDIUM = "medium.ttf"
    private const val BOLD = "bold.ttf"
    private const val EXTRA_BOLD = "extra_bold.ttf"

    val medium by lazy { Typeface.createFromAsset(BiteApp.instance.assets, MEDIUM) }
    val bold by lazy { Typeface.createFromAsset(BiteApp.instance.assets, BOLD) }
    val extraBold by lazy { Typeface.createFromAsset(BiteApp.instance.assets, EXTRA_BOLD) }
}