package com.job4j.weather_app.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.job4j.weather_app.R
import com.job4j.weather_app.REQUEST_LOCATION_PERMISSION
import com.job4j.weather_app.adapter.CurrentAdapter
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

    private lateinit var currentMain : TextView
    private lateinit var currentTemp : TextView
    private lateinit var currentName : TextView
    private lateinit var currentDescription : TextView
    private lateinit var currentIcon : LottieAnimationView
    private lateinit var currentRecycler : RecyclerView
    private lateinit var btnForecast : Button
    private lateinit var currentImageView : ImageView
    private lateinit var currentProgress : LottieAnimationView
    private lateinit var btnLocation : CardView
    private lateinit var btnSearch : CardView
    private var lat : String = ""
    private var lon : String = ""

    private lateinit var animTop : Animation
    private lateinit var animCenter : Animation
    private lateinit var animBottom : Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: initialization MainFragment.")
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        animTop = AnimationUtils.loadAnimation(context, R.anim.anim_current_top)
        animCenter = AnimationUtils.loadAnimation(context, R.anim.anim_current_center)
        animBottom = AnimationUtils.loadAnimation(context, R.anim.anim_current_bottom)

        currentMain = view.findViewById(R.id.current_main)
        currentTemp = view.findViewById(R.id.current_temp)
        currentName = view.findViewById(R.id.current_name)
        currentDescription = view.findViewById(R.id.current_description)
        currentIcon = view.findViewById(R.id.current_icon)
        currentRecycler = view.findViewById(R.id.recycler_current)
        btnForecast = view.findViewById(R.id.btn_forecast)
        btnForecast.setOnClickListener(this::onClickForecast)
        currentImageView = view.findViewById(R.id.current_image_view)
        currentProgress = view.findViewById(R.id.current_progress_anim)

        getLocationPermission()
        if (locationPermissionGranted) {
            updateUI()

            btnLocation = view.findViewById(R.id.layout_location)
            btnLocation.setOnClickListener(this::onClickLocation)
            btnSearch = view.findViewById(R.id.layout_search)
            setSearchLocation()
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

    private val currentWeatherResponse : MutableLiveData<CurrentWeather>
        get() = callbackCurrentWeather

    private fun updateUI() {
        val location = AppLocationManager(context!!)
        lat = location.getLatitude()
        lon = location.getLongitude()

        RequestWeather().getCurrentWeather(location.getLatitude(), location.getLongitude(), callbackCurrentWeather)
        currentWeatherResponse.observe(viewLifecycleOwner, Observer {
                currentWeather: CurrentWeather ->
            initAnimation()

            currentMain.text = currentWeather.weather!![0].main.trim()
            currentDescription.text = currentWeather.weather!![0].description.trim()
            currentTemp.text = String.format("%s°", currentWeather.main!!.temp.toInt())
            currentName.text = currentWeather.name.trim()
            currentIcon.setAnimation("${currentWeather.weather!![0].icon}.json")

            currentRecycler.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)
            val adapter = CurrentAdapter(context!!, R.layout.view_current,
                initInfoMap(currentWeather.wind?.speed, currentWeather.main?.humidity,
                currentWeather.main?.pressure, currentWeather.main?.tempMin,
                    currentWeather.main?.tempMax))
            currentRecycler.adapter = adapter

            currentProgress.visibility = View.GONE
            currentImageView.visibility = View.VISIBLE
            btnForecast.visibility = View.VISIBLE
            btnLocation.visibility = View.VISIBLE
            btnSearch.visibility = View.VISIBLE
        })
    }

    private fun initAnimation() {
        currentMain.startAnimation(animTop)
        currentDescription.startAnimation(animTop)
        currentIcon.startAnimation(animCenter)
        currentName.startAnimation(animCenter)
        currentTemp.startAnimation(animCenter)
        currentRecycler.startAnimation(animCenter)
        currentImageView.startAnimation(animBottom)
        btnForecast.startAnimation(animBottom)
    }

    private fun initInfoMap(wind: Double?, humidity: Double?, pressure: Double?, min: Double?,
                            max: Double?) : Map<String, String> {
        return mapOf(
            getString(R.string.title_wind) to String.format("%skm/h", wind?.toInt()),
            getString(R.string.title_humidity) to String.format("%s%%", humidity?.toInt()),
            getString(R.string.title_pressure) to String.format("%smm", pressure?.toInt()),
            getString(R.string.title_min_temp) to String.format("%s°С", min?.toInt()),
            getString(R.string.title_max_temp) to String.format("%s°С", max?.toInt())
        )
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickForecast(v: View) {
        val bundle = Bundle()
        bundle.putString("current_latitude", lat)
        bundle.putString("current_longitude", lon)

        val forecastFragment = ForecastBottomFragment()
        forecastFragment.arguments = bundle
        activity?.supportFragmentManager?.let {
            forecastFragment.show(it, "forecast_fragment") }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickLocation(v: View) {
        Log.d(TAG, "onClickLocation: click location.")
        getLocationPermission()
        if (locationPermissionGranted) {
            updateUI()
        }
    }

    private fun setSearchLocation() {
        Places.initialize(context!!.applicationContext, getString(R.string.google_maps_key))
        val searchPlace : AutocompleteSupportFragment = childFragmentManager.findFragmentById(
            R.id.autocomplete_fragment) as AutocompleteSupportFragment
        searchPlace.setPlaceFields(arrayListOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        searchPlace.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.d(TAG, "onClickSearch: onPlaceSelected: place = $place")
            }

            override fun onError(status: Status) {
                Log.e(TAG, "onClickSearch: onError: status = $status")
            }
        })
    }
}