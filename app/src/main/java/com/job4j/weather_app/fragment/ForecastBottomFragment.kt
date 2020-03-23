package com.job4j.weather_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.job4j.weather_app.R
import com.job4j.weather_app.adapter.ForecastAdapter
import com.job4j.weather_app.model.Day
import com.job4j.weather_app.model.ForecastWeather
import com.job4j.weather_app.network.RequestWeather

/**
 * Класс ForecastBottomFragment - фрагмент с прогнозом погоды на 5 дней
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 19.03.2020
 * @version $Id$
 */

class ForecastBottomFragment : BottomSheetDialogFragment() {
    private val callbackForecastWeather = MutableLiveData<ForecastWeather>()
    private val requestWeather = RequestWeather()

    private lateinit var forecastRecycler : RecyclerView
    private lateinit var progressForecast : ProgressBar
    private lateinit var animationRecycler : Animation

    fun newInstance(lat: String, lon: String) : ForecastBottomFragment {
        val bundle = Bundle()
        bundle.putString("latitude_forecast", lat)
        bundle.putString("longitude_forecast", lon)

        val fragment = ForecastBottomFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_forecast, container, false)

        forecastRecycler = view.findViewById(R.id.recycler_forecast)
        progressForecast = view.findViewById(R.id.progress_forecast)
        animationRecycler = AnimationUtils.loadAnimation(context, R.anim.anim_forecast_recycler)

        updateUI(arguments?.get("latitude_forecast").toString(),
            arguments?.get("longitude_forecast").toString())

        return view
    }

    private fun updateUI(lat: String, lon: String) {
        requestWeather.getForecastWeather(lat, lon, callbackForecastWeather)
        forecastWeatherResponse.observe(viewLifecycleOwner, Observer {
                forecastWeather: ForecastWeather? ->
            forecastWeather?.let {
                val days: List<Day>? = forecastWeather.days
                days?.let {
                    forecastRecycler.startAnimation(animationRecycler)
                    forecastRecycler.layoutManager = LinearLayoutManager(context)
                    forecastRecycler.adapter = ForecastAdapter(context!!, R.layout.view_forecast, days)
                }
            }
            progressForecast.visibility = View.GONE
        })
    }

    private val forecastWeatherResponse : MutableLiveData<ForecastWeather>
        get() = callbackForecastWeather
}