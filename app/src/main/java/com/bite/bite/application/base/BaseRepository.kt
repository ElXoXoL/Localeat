package com.bite.bite.application.base

import android.util.Log
import com.bite.bite.koin.KoinComponents
import retrofit2.Response
import com.bite.bite.models.Result
import com.bite.bite.utils.LogType
import java.io.IOException

open class BaseRepository{
    
    val logger = KoinComponents.logger

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result : Result<T> = safeApiResult(call,errorMessage)
        var data : T? = null

        logger.log(result, LogType.ApiCall)

        when(result) {
            is Result.Success ->
                data = result.data
            is Result.Error -> {
                Log.d("1.DataRepository", "$errorMessage & Exception - ${result.exception}")
            }
        }


        return data

    }

    private suspend fun <T: Any> safeApiResult(call: suspend () -> Response<T>, errorMessage: String) : Result<T>{
        val response = call.invoke()
        if(response.isSuccessful) return Result.Success(response.body()!!)

        return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }
}