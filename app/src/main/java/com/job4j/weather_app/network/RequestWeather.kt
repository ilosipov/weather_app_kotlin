package com.job4j.weather_app.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.job4j.weather_app.KEY_API
import com.job4j.weather_app.UNITS
import com.job4j.weather_app.WEATHER_URL
import com.job4j.weather_app.model.CurrentWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Класс RequestWeather - выполняет запрос данных погоды
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 16.03.2020
 * @version $Id$
 */

class RequestWeather {
    private val TAG = "RequestWeather"
    private val retrofit : Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(WEATHER_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getCurrentWeather(lat: String, lon: String, callback: MutableLiveData<CurrentWeather>) {
        weatherApi.weather(lat, lon, UNITS, KEY_API)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<CurrentWeather>() {
                override fun onSuccess(currentWeather: CurrentWeather) {
                    Log.d(TAG, "onSuccess: $currentWeather")
                    callback.postValue(currentWeather)
                }

                override fun onError(error: Throwable) {
                    Log.e(TAG, "onError: $error")
                    error.printStackTrace()
                }

            })
    }

    val weatherApi: WeatherApi
        get() = retrofit.create(WeatherApi::class.java)
}