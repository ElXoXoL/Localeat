package com.bite.bite.coroutines

import kotlinx.coroutines.Job

interface CoroutineProvider{

    suspend fun onMainThread(function: () -> Unit)
    fun onWorkerThread(function: suspend () -> Unit): Job
}