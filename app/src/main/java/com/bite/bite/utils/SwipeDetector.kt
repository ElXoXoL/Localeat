package com.bite.bite.utils

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

class SwipeDetector(val onSwipe: () -> (Unit)) : GestureDetector.SimpleOnGestureListener() {

    companion object {

        private const val SWIPE_MIN_DISTANCE = 300
        private const val SWIPE_MAX_OFF_PATH = 250
        private const val SWIPE_THRESHOLD_VELOCITY = 500
    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean { // Check movement along the Y-axis. If it exceeds SWIPE_MAX_OFF_PATH,
// then dismiss the swipe.
        if (abs(e2.x - e1.x) > SWIPE_MAX_OFF_PATH) return false
        // Swipe from left to right.
// The swipe needs to exceed a certain distance (SWIPE_MIN_DISTANCE)
// and a certain velocity (SWIPE_THRESHOLD_VELOCITY).
        if (e2.y - e1.y > SWIPE_MIN_DISTANCE && abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            onSwipe()
        }
        return false
    }
}