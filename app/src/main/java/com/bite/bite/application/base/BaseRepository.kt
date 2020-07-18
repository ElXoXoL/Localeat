package com.bite.bite.application.base

import android.util.Log
import retrofit2.Response
import com.bite.bite.models.Result
import java.io.IOException

open class BaseRepository{

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, onError: (() -> (Unit))? = null): T? {

        val result : Result<T> = safeApiResult(call)
        var data : T? = null

        when(result) {
            is Result.Success ->
                data = result.data
            is Result.Error -> {
                if (onError != null)
                    onError()

                Log.d("1.DataRepository", "Exception - ${result.exception}")
            }
        }


        return data

    }

    private suspend fun <T: Any> safeApiResult(call: suspend () -> Response<T>) : Result<T>{
        val response = call.invoke()
        if(response.isSuccessful) return Result.Success(response.body()!!)

        return Result.Error(IOException("Error Occurred during getting safe Api result"))
    }
}