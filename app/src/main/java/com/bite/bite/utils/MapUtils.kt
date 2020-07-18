package com.bite.bite.utils

import android.content.Context
import android.graphics.*
import android.graphics.Paint.Align
import com.bite.bite.R
import com.bite.bite.application.BiteApp
import com.bite.bite.application.extensions.drawable
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class MapUtils(val context: Context) {

    val markerIconDefault by lazy {
        BitmapDescriptorFactory.fromBitmap(getMarkerIconDefaultBitmap())
    }

    private fun getMarkerIconDefaultBitmap(): Bitmap{
        // retrieve the actual drawable
        val drawable = context.drawable(R.drawable.ic_marker)!!

        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bm = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

        // draw it onto the bitmap
        val canvas = Canvas(bm)
        drawable.draw(canvas)
        return bm
    }

    private val markerBitmap by lazy { BitmapFactory.decodeResource(BiteApp.instance.resources, R.drawable.ic_marker) }

}