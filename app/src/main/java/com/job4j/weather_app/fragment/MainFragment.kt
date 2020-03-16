package com.job4j.weather_app.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        initLocation()

        return view
    }

    private fun initLocation() {
        val location = AppLocationManager(this.context!!)
        Log.d(TAG, "onCreateView: latitude = ${location.getLatitude()}")
        Log.d(TAG, "onCreateView: longitude = ${location.getLongitude()}")
    }
}