package com.bite.bite.application

import android.app.Application
import com.bite.bite.koin.app
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BiteApp: Application(){

    companion object {
        lateinit var instance: BiteApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            // declare used Android context
            androidLogger()
            androidContext(this@BiteApp)
            // declare modules
            modules(
                app
            )
        }
    }

//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(LocaleUtils.setLocale(base))
//        instance = this
//    }
//
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        LocaleUtils.setLocale(this)
//        instance = this
//    }
}