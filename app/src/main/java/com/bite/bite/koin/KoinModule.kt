package com.bite.bite.koin

import com.bite.bite.api.NetworkRepository
import com.bite.bite.coroutines.CoroutineProvider
import com.bite.bite.coroutines.CoroutinesExecutor
import com.bite.bite.ui.MainViewModel
import com.bite.bite.utils.LocationUtils
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
//    single { AuthUtils() }
//    single { FirestoreManager() }
//    single { StorageUtils() }
    single { CoroutinesExecutor() as CoroutineProvider }
    single { LocationUtils() }
    single { NetworkRepository() }

    viewModel { MainViewModel(get()) }
//
//    viewModel { TestViewModel(get()) }
}

val app = listOf(appModule)