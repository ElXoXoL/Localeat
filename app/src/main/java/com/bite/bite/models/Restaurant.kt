package com.bite.bite.models

import android.graphics.Bitmap

data class Restaurant(
    val id: Int,
    val name: String,
    val img: String?,
    val description: String,
    val rating: Int,
    val lat: String,
    val lng: String,
    val address_name: String
    )