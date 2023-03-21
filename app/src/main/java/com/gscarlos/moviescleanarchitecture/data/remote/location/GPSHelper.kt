package com.gscarlos.moviescleanarchitecture.data.remote.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.gscarlos.moviescleanarchitecture.common.Constants.EVERY_MINUTES_TAKE_LOCATION

class GPSHelper {
    private var LOCATION_REFRESH_TIME = EVERY_MINUTES_TAKE_LOCATION * 60 * 1000
    private var LOCATION_REFRESH_DISTANCE = 0

    @SuppressLint("MissingPermission")
    fun startListeningUserLocation(context: Context, onChange: (Location) -> Unit) {
        val mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = LocationListener { location ->
            onChange(location)
        }
        mLocationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            LOCATION_REFRESH_TIME.toLong(),
            LOCATION_REFRESH_DISTANCE.toFloat(),
            locationListener
        )
    }
}