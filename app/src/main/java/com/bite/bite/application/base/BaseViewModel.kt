package com.bite.bite.application.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel(){

    protected val compositeDisposable by lazy { CompositeDisposable() }

//    protected val firestoreManager = KoinComponents.firestoreManager
//    protected val storageUtils = KoinComponents.storageUtils

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}