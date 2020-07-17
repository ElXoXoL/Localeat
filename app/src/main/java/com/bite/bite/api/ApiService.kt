package com.bite.bite.api

import com.bite.bite.R
import com.bite.bite.application.BiteApp
import com.bite.bite.models.Restaurant
import com.bite.bite.models.Version
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface ApiService {

    companion object Factory {

        private val URL = BiteApp.instance.getString(R.string.server_url)
        private const val SERVER_API = "api/main/"

        fun create(): ApiService {
            val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

    @GET("${SERVER_API}restaurants/")
    fun getRestaurants() : Deferred<Response<List<Restaurant>>>

    @GET("api/customers/app_version/")
    fun getVersion(): Deferred<Response<List<Version>>>
}