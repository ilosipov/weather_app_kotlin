package com.job4j.weather_app.model

import com.google.gson.annotations.SerializedName

/**
 * Класс Day - модель информации погоды на день
 * @author Ilya Osipov
 */

data class Day(
    @SerializedName("main")
    var main: Main? = null,
    @SerializedName("weather")
    var weather: List<Weather>? = null,
    @SerializedName("wind")
    var wind: Wind? = null,
    @SerializedName("dt_txt")
    var dt_txt: String = "") {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Day

        if (main != other.main) return false
        if (weather != other.weather) return false
        if (wind != other.wind) return false
        if (dt_txt != other.dt_txt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = main?.hashCode() ?: 0
        result = 31 * result + (weather?.hashCode() ?: 0)
        result = 31 * result + (wind?.hashCode() ?: 0)
        result = 31 * result + dt_txt.hashCode()
        return result
    }

    override fun toString(): String {
        return " \nDay: main = $main, \nweather = $weather, \nwind = $wind, dt_txt = $dt_txt \n"
    }
}