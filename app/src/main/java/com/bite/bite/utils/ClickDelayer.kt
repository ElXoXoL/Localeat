package com.bite.bite.utils

import android.os.SystemClock

class ClickDelayer(private val delay: Long, private var onClick: () -> Unit) {

    // Disable on back press click for some millis
    // It sets assigned value plus current system time
    private var disabledTimeClick: Long? = null
        set(value) {
            field = if (value != null) value + SystemClock.elapsedRealtime() else null
        }

    fun changeClick(onClick: () -> Unit){
        this.onClick = onClick
    }

    fun callClick(){
        // Check if back press is allowed
        if (disabledTimeClick != null && SystemClock.elapsedRealtime() < disabledTimeClick!!)
            return

        disabledTimeClick = delay
        onClick()
    }
}