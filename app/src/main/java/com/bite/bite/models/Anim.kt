package com.bite.bite.models

import androidx.cardview.widget.CardView

sealed class Anim {
    data class CircularShow(
        val x: Int,
        val y: Int,
        val rad: Float,
        val view: CardView
    ) : Anim()
    data class CircularHide(
        val x: Int,
        val y: Int,
        val rad: Float,
        val view: CardView,
        val onHideAction: () -> Unit
    ) : Anim()
    object NoAnim: Anim()
}