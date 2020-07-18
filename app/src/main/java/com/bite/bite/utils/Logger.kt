package com.bite.bite.utils

import android.util.Log
import com.bite.bite.BuildConfig

enum class LogType {
    Default,
    FuncCall,
    ApiCall
}

class Logger {

    private val isLogsEnabled = BuildConfig.DEBUG

    fun log(obj: Any?, logType: LogType = LogType.Default){
        if (isLogsEnabled) Log.d(logType.name, obj.toString())
    }

}