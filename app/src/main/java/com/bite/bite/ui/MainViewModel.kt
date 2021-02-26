package com.bite.bite.ui

import androidx.lifecycle.MutableLiveData
import com.bite.bite.api.NetworkRepository
import com.bite.bite.application.base.BaseViewModel
import com.bite.bite.application.runOnWorker
import com.bite.bite.models.*
import com.bite.bite.utils.LogType
import com.bite.bite.utils.Logger
import com.google.android.gms.maps.model.Marker

class MainViewModel(
    private val networkRepository: NetworkRepository,
    private val logger: Logger
    ) : BaseViewModel(){

    val isError = MutableLiveData<Boolean>()

    val restaurantList = MutableLiveData<MutableList<RestaurantObj>?>()
    val markerList = mutableListOf<Marker>()

    val recents = MutableLiveData<MutableList<RestaurantObj>?>()

    val selectedRestaurant = MutableLiveData<RestaurantObj?>()

    // Food types for menu, map and list
    val foodTypes = MutableLiveData<MutableList<FoodType>?>()

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
    }

    private fun getRestaurants(){
        logger.log("$this getRestaurants", LogType.FuncCall)
        runOnWorker {
            val response = networkRepository.getRestaurants{
                isError.postValue(true)
            }?.map {
                RestaurantObj(it)
            }?.toMutableList()

            restaurantList.postValue(response)
            recents.postValue(response)
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

    // Selects restaurant from position
    fun selectRestaurant(pos: Int){
        selectedRestaurant.value = restaurantList.value!![pos]
    }

    // Cleans restaurant selection
    fun cleanRestaurant(){
        selectedRestaurant.value = null
    }
}