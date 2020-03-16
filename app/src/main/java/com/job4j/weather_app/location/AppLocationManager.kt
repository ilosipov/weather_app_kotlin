@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.job4j.weather_app.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat

/**
 * Класс AppLocationManager - получает координаты места нахождения
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 16.03.2020
 * @version $Id$
 */

class AppLocationManager(context: Context) : LocationListener {
    private val locationManager : LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val criteria : Criteria = Criteria()
    private lateinit var longitude : String
    private lateinit var latitude : String
    private val provider : String

    init {
        criteria.accuracy = Criteria.ACCURACY_FINE
        provider = locationManager.getBestProvider(criteria, true)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0F, this)
            setMostRecentLocation(locationManager.getLastKnownLocation(provider))
        }
    }

    private fun setMostRecentLocation(lastKnowLocation: Location?) {}

    fun getLatitude() : String {
        return latitude
    }

    fun getLongitude() : String {
        return longitude
    }

    override fun onLocationChanged(location: Location?) {
        longitude = "${location?.longitude}"
        latitude = "${location?.latitude}"
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("Not yet implemented")
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("Not yet implemented")
    }
}
