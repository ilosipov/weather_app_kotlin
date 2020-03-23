package com.job4j.weather_app.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
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

@Suppress("ControlFlowWithEmptyBody")
class AppLocationManager(context: Context) : LocationListener {

    private var locationManager : LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var longitude : String = ""
    private var latitude : String = ""

    init {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
        }
        var location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            onLocationChanged(location)
        } else {
            onLocationChanged(location)
        }
    }

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
