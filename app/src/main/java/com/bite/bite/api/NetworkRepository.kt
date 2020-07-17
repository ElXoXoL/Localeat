package com.bite.bite.api

import com.bite.bite.application.base.BaseRepository
import com.bite.bite.models.Restaurant
import com.bite.bite.models.Version
import com.bite.bite.utils.Logger
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

class NetworkRepository: BaseRepository(){

    companion object{
        private const val TAG = "ApiService"
    }

    private val apiService by lazy { ApiService.create() }

    suspend fun getRestaurants(): MutableList<Restaurant>?{
        val response = safeApiCall(
            call = {apiService.getRestaurants().await()},
            errorMessage = "Error getting restaurants"
        )
        return response?.toMutableList()
    }
}