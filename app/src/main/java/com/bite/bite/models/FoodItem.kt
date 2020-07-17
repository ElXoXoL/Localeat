package com.bite.bite.models

data class FoodItem(
    val id: Int,
    val typeId: Int,
    val name: String,
    val descr: String,
    val price: Float
)