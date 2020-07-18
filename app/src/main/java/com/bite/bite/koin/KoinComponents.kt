package com.bite.bite.koin

import com.bite.bite.api.NetworkRepository
import com.bite.bite.coroutines.CoroutineProvider
import com.bite.bite.utils.LocationUtils
import com.bite.bite.utils.Logger
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Components object to be able to Mock it later in testing
 */

object KoinComponents: KoinComponent {
//    val firestoreManager : FirestoreManager by inject ()
//    val storageUtils : StorageUtils by inject ()
//    val authUtils: AuthUtils by inject()
    val coroutineProvider: CoroutineProvider by inject()
    val locationUtils: LocationUtils by inject()
    val networkRepository: NetworkRepository by inject()

    val logger: Logger by inject()
}