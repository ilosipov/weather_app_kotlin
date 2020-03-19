package com.job4j.weather_app.model

import com.google.gson.annotations.SerializedName

/**
 * Класс Forecast - модель 5 дневной погоды
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 16.03.2020
 * @version $Id$
 */

data class ForecastWeather(
    @SerializedName("list")
    val days: List<Day>? = null) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ForecastWeather

        if (days != other.days) return false

        return true
    }

    override fun hashCode(): Int {
        return days?.hashCode() ?: 0
    }

    override fun toString(): String {
        return " \nForecastWeather: \ndays = $days"
    }
}