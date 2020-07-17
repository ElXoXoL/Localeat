package com.bite.bite.application

import android.os.Handler
import com.bite.bite.koin.KoinComponents
import com.bite.bite.utils.Logger
import kotlinx.coroutines.Job
import java.lang.Exception


/**
 * My most favourite functions, that I prefer to use
 */

fun runOnWorker(function: suspend () -> Unit): Job {
    return KoinComponents.coroutineProvider.onWorkerThread { function() }
}

suspend fun runOnMain(function: () -> Unit) {
    return KoinComponents.coroutineProvider.onMainThread { function() }
}

fun delayFunc(time: Long, function: () -> Unit){
        Handler().postDelayed({
            try { function() }
            catch (e: Exception){ Logger.log(e) }
        }, time)
}