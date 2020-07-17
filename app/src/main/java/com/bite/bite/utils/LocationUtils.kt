package com.bite.bite.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class LocationUtils{

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1

        const val ZOOM_DEFAULT = 13f
        const val ZOOM_DEFAULT_USER = 15f
    }

    fun isPermissionGranted(context: Activity?): Boolean {
        return context != null &&
                ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(context: Activity?){
        if (context == null) return

        ActivityCompat.requestPermissions(context,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
    }
}