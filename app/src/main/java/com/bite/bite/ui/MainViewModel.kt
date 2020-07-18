package com.bite.bite.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import com.bite.bite.api.NetworkRepository
import com.bite.bite.application.base.BaseViewModel
import com.bite.bite.application.runOnWorker
import com.bite.bite.models.*
import com.bite.bite.ui.list.AdapterRestaurants
import com.bite.bite.utils.LogType
import com.bite.bite.utils.Logger
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.withContext

class MainViewModel(
    private val networkRepository: NetworkRepository,
    private val logger: Logger
    ) : BaseViewModel(){

    val restaurantList = MutableLiveData<MutableList<RestaurantObj>?>()
    val markerList = mutableListOf<Marker>()

    val sales = MutableLiveData<MutableList<RestaurantObj>?>()

    val selectedRestaurant = MutableLiveData<RestaurantObj?>()

    // Food types for menu, map and list
    val foodTypes = MutableLiveData<MutableList<FoodType>?>()

    // Selected food type in menu
    val selectedFoodType = MutableLiveData<FoodType>()

    // Menu items
    val foodItems = MutableLiveData<MutableList<FoodItem>?>()

    val contacts = MutableLiveData<MutableList<Contact>?>()

    // Boolean for checking if any view is scrolling to be able to cancel swipe event
    var isCurrentlyScrolling = false

    // Selected food type in menu
    private var selectedFoodTypePos = -1
    set(value) {
        isFromRight = when {
            field == -1 -> null
            value > field -> true
            value < field -> false
            else -> null
        }
        field = value
    }
    // Animation from right or left for menu items
    var isFromRight: Boolean? = null

    init {
        getRestaurants()
        getFoodTypes()
        getSales()
        getFoodItems()
        getContacts()
    }

    private fun getRestaurants(){
        logger.log("$this getRestaurants", LogType.FuncCall)
        runOnWorker {
            val response = networkRepository.getRestaurants()?.map {
                RestaurantObj(it)
            }?.toMutableList()
            response?.add(response[0])
            response?.add(response[0])
            response?.add(response[0])
            response?.add(response[0])
            response?.add(response[0])
            response?.add(response[0])

            restaurantList.postValue(response)
            sales.postValue(response)
        }
    }

    private fun getFoodTypes(){
        logger.log("$this getFoodTypes", LogType.FuncCall)
        runOnWorker {
//            val response = networkRepository.getRestaurants()
            val response = mutableListOf(
                FoodType(0, "Все"),
                FoodType(1, "Салаты"),
                FoodType(2, "Пицца"),
                FoodType(3, "Пасты"),
                FoodType(4, "Закуски"),
                FoodType(5, "Рыба"),
                FoodType(6, "Мясо")
            )

            foodTypes.postValue(response)
        }
    }

    private fun getSales(){
        logger.log("$this getSales", LogType.FuncCall)
        runOnWorker {
            //            val response = networkRepository.getRestaurants()
            val response = mutableListOf(
                Sales(1, "https://media-cdn.tripadvisor.com/media/photo-s/0e/ae/21/d9/getlstd-property-photo.jpg", "PestoCafe", "Акций: 2"),
                Sales(2, "https://media-cdn.tripadvisor.com/media/photo-s/0e/ae/21/d9/getlstd-property-photo.jpg", "PestoCafe", "Акций: 2"),
                Sales(3, "https://media-cdn.tripadvisor.com/media/photo-s/0e/ae/21/d9/getlstd-property-photo.jpg", "PestoCafe", "Акций: 2"),
                Sales(4, "https://media-cdn.tripadvisor.com/media/photo-s/0e/ae/21/d9/getlstd-property-photo.jpg", "PestoCafe", "Акций: 2"),
                Sales(5, "https://media-cdn.tripadvisor.com/media/photo-s/0e/ae/21/d9/getlstd-property-photo.jpg", "PestoCafe", "Акций: 2"),
                Sales(6, "https://media-cdn.tripadvisor.com/media/photo-s/0e/ae/21/d9/getlstd-property-photo.jpg", "PestoCafe", "Акций: 2"),
                Sales(7, "https://media-cdn.tripadvisor.com/media/photo-s/0e/ae/21/d9/getlstd-property-photo.jpg", "PestoCafe", "Акций: 2")
            )

        }
    }

    private fun getFoodItems(){
        logger.log("$this getFoodItems", LogType.FuncCall)
        runOnWorker {
            //            val response = networkRepository.getRestaurants()
            val response = mutableListOf(
                FoodItem(1, 1, "Цезарь", "390 г.", 150f),
                FoodItem(1, 1, "Цезарь", "390 г.", 150f),
                FoodItem(1, 1, "Цезарь", "390 г.", 150f),
                FoodItem(1, 1, "Цезарь", "390 г.", 150f),
                FoodItem(1, 1, "Цезарь", "390 г.", 150f),
                FoodItem(1, 1, "Цезарь", "390 г.", 150f),
                FoodItem(1, 1, "Цезарь", "390 г.", 150f),
                FoodItem(1, 1, "Цезарь", "390 г.", 150f),
                FoodItem(1, 1, "Цезарь", "390 г.", 150f),
                FoodItem(1, 1, "Цезарь", "390 г.", 150f),
                FoodItem(1, 1, "Цезарь", "390 г.", 150f),
                FoodItem(1, 1, "Цезарь", "390 г.", 150f)
            )

            foodItems.postValue(response)
        }
    }

    private fun getContacts(){
        logger.log("$this getContacts", LogType.FuncCall)
        runOnWorker {
            //            val response = networkRepository.getRestaurants()
            val response = mutableListOf(
                Contact(1, "+38 (067) 322 13 32"),
                Contact(2, "+38 (067) 322 13 32")
            )

            contacts.postValue(response)
        }
    }

    // Selects restaurant from position
    fun selectRestaurant(pos: Int){
        selectedRestaurant.value = restaurantList.value!![pos]
    }

    // Selects food type from position
    fun selectFoodType(pos: Int){
        if (selectedFoodTypePos == pos) return

        selectedFoodTypePos = pos
        selectedFoodType.value = foodTypes.value!![pos]
    }

    // Sets default food type
    fun defaultFoodType(){
        selectedFoodTypePos = -1
        isFromRight = null
    }

    // Cleans restaurant selection
    fun cleanRestaurant(){
        selectedRestaurant.value = null
    }
}