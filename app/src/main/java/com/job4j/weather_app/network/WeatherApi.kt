package com.job4j.weather_app.network

import com.job4j.weather_app.model.CurrentWeather
import com.job4j.weather_app.model.ForecastWeather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Интерфейс WeatherApi
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 16.03.2020
 * @version $Id$
 */

interface WeatherApi {

    @GET("weather")
    fun weather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("appid") key: String) : Single<CurrentWeather>

    @GET("forecast")
    fun forecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("appid") key: String) : Single<ForecastWeather>
}