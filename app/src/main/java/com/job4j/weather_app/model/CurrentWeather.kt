package com.job4j.weather_app.model

import com.google.gson.annotations.SerializedName

/**
 * Класс CurrentWeather - модель текущей погоды
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 16.03.2020
 * @version $Id$
 */

data class CurrentWeather(
    @SerializedName("weather")
    var weather: List<Weather>? = null,
    @SerializedName("main")
    var main: Main? = null,
    @SerializedName("wind")
    var wind: Wind? = null,
    @SerializedName("name")
    var name: String = "") {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrentWeather

        if (weather != other.weather) return false
        if (main != other.main) return false
        if (wind != other.wind) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = weather?.hashCode() ?: 0
        result = 31 * result + (main?.hashCode() ?: 0)
        result = 31 * result + (wind.hashCode() ?: 0)
        result = 31 * result + name.hashCode()
        return result
    }


    override fun toString(): String {
        return "CurrentWeather: weather = $weather, main = $main, wind = $wind, name = $name"
    }
}