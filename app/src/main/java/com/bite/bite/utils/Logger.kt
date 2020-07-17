package com.bite.bite.utils

import android.util.Log

object Logger {

    fun log(obj: Any?){
        Log.d("TEMPTAG", obj.toString())
    }

}