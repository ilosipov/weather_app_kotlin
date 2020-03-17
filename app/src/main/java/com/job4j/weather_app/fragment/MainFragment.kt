package com.job4j.weather_app.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.job4j.weather_app.R
import com.job4j.weather_app.REQUEST_LOCATION_PERMISSION
import com.job4j.weather_app.location.AppLocationManager
import com.job4j.weather_app.model.CurrentWeather
import com.job4j.weather_app.network.RequestWeather

/**
 * Класс MainFragment - реализует представление главного экрана
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 16.03.2020
 * @verison $Id$
 */

class MainFragment : Fragment() {
    private val TAG = "MainFragment"
    private var locationPermissionGranted = false
    private val callbackCurrentWeather = MutableLiveData<CurrentWeather>()

    private lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: initialization MainFragment.")
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        textView = view.findViewById(R.id.textView)

        getLocationPermission()
        if (locationPermissionGranted) {
            updateUI()
        }
        return view
    }

    private fun getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permission.")
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (activity != null) {
            if (ContextCompat.checkSelfPermission(activity!!.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity!!.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, REQUEST_LOCATION_PERMISSION)
                Log.d(TAG, "getLocationPermission: request location false.")
            } else {
                locationPermissionGranted = true
                Log.d(TAG, "getLocationPermission: request location true.")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray) {
        Log.d(TAG, "onRequestPermissionsResult: called.")
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateUI()
                    locationPermissionGranted = true
                    Log.d(TAG, "onRequestPermissionsResult: locations permission true.")
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: location permission false.")
                }
            }
        }
    }

    private fun updateUI() {
        Log.d(TAG, "updateUI")
        val location = AppLocationManager(context!!)
        Log.d(TAG, "onCreateView: latitude = ${location.getLatitude()}")
        Log.d(TAG, "onCreateView: longitude = ${location.getLongitude()}")

        textView.text = String.format("latitude: %s; longitude: %s", location.getLatitude(), location.getLongitude())
        RequestWeather().getCurrentWeather(location.getLatitude(), location.getLongitude(), callbackCurrentWeather)
    }
}