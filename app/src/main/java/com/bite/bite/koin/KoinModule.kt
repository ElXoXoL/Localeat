package com.bite.bite.koin

import androidx.appcompat.app.AppCompatActivity
import com.bite.bite.api.NetworkRepository
import com.bite.bite.coroutines.CoroutineProvider
import com.bite.bite.coroutines.CoroutinesExecutor
import com.bite.bite.ui.MainViewModel
import com.bite.bite.ui.restaurant.RestaurantViewModel
import com.bite.bite.utils.FragmentUtils
import com.bite.bite.utils.LocationUtils
import com.bite.bite.utils.Logger
import com.bite.bite.utils.MapUtils
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
//    single { AuthUtils() }
//    single { FirestoreManager() }
//    single { StorageUtils() }
    single { CoroutinesExecutor() as CoroutineProvider }
    single { LocationUtils() }

    single { Logger() }

    single { NetworkRepository() }

    single { MapUtils(get()) }

    factory { (activity: AppCompatActivity) -> FragmentUtils(activity) }

    viewModel { MainViewModel(get(), get()) }

    viewModel { RestaurantViewModel(get(), get(), get()) }

}

val app = listOf(appModule)