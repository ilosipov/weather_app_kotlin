package com.job4j.weather_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private lateinit var forecastRecycler : RecyclerView
    private lateinit var progressForecast : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_forecast, container, false)

        forecastRecycler = view.findViewById(R.id.recycler_forecast)
        progressForecast = view.findViewById(R.id.progress_forecast)

        return view
    }

    private fun updateUI(lat: String, lon: String) {
        RequestWeather().getForecastWeather(lat, lon, callbackForecastWeather)
        forecastWeatherResponse.observe(viewLifecycleOwner, Observer {
                forecastWeather: ForecastWeather? ->
            forecastWeather?.let {
                val days: List<Day>? = forecastWeather.days
                days?.let {
                    forecastRecycler.layoutManager = LinearLayoutManager(context)
                    forecastRecycler.adapter = ForecastAdapter(context!!, R.layout.view_forecast, days)
                }
            }
            progressForecast.visibility = View.GONE
        })
    }

    private val forecastWeatherResponse : MutableLiveData<ForecastWeather>
        get() = callbackForecastWeather

    override fun onStart() {
        super.onStart()
        val bundle = arguments
        if (bundle != null) {
            updateUI(
                bundle.getString("current_latitude", ""),
                bundle.getString("current_longitude", "")
            )
        }
    }
}