package com.bite.bite.utils

import android.graphics.Color
import android.view.animation.LinearInterpolator
import androidx.transition.Fade
import androidx.transition.Slide
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform

object TransitionUtils{

    private const val time: Long = 400

    val fadeLinear by lazy {
        Fade().apply {
            duration = time
        }
    }

    val hold by lazy {
        Hold().apply {
            duration = time
        }
    }

    val slide by lazy {
        Slide().apply {
            duration = time
        }
    }

    val containerTransform by lazy {
        MaterialContainerTransform().apply {
            duration = time
            interpolator = LinearInterpolator()
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }
    }
}