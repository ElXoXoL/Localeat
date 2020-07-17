package com.bite.bite.coroutines

import kotlinx.coroutines.*

class CoroutinesExecutor: CoroutineProvider {
    override suspend fun onMainThread(function: () -> Unit) {
            withContext(Dispatchers.Main) {
                function.invoke()
            }
    }

    override fun onWorkerThread(function: suspend () -> Unit): Job  {
        return GlobalScope.async(Dispatchers.IO) {
            function.invoke()
        }
    }


}