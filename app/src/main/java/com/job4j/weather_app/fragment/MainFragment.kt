package com.job4j.weather_app.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.job4j.weather_app.LOCATION_PERMISSION_CODE
import com.job4j.weather_app.R
import com.job4j.weather_app.location.AppLocationManager

/**
 * Класс MainFragment - реализует представление главного экрана
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 16.03.2020
 * @verison $Id$
 */

class MainFragment : Fragment() {
    private val TAG = "MainFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: initialization MainFragment.")
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        getLocationPermission()

        return view
    }

    private fun getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permission.")
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (activity != null) {
            if (ContextCompat.checkSelfPermission(activity!!.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(activity!!.applicationContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    initLocation()
                } else {
                    ActivityCompat.requestPermissions(activity!!, permissions, LOCATION_PERMISSION_CODE)
                }
            } else {
                ActivityCompat.requestPermissions(activity!!, permissions, LOCATION_PERMISSION_CODE)
            }
        }
    }

    private fun initLocation() {
        val location = AppLocationManager(context!!)
        Log.d(TAG, "onCreateView: latitude = ${location.getLatitude()}")
        Log.d(TAG, "onCreateView: longitude = ${location.getLongitude()}")
    }
}