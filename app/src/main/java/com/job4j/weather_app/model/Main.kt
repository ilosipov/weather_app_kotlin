package com.job4j.weather_app.model

import com.google.gson.annotations.SerializedName

/**
 * Класс Main - медель данных погоды
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 16.03.2020
 * @version $Id$
 */

data class Main(
    @SerializedName("temp")
    var temp: Double = 0.0,
    @SerializedName("pressure")
    var pressure: Double = 0.0,
    @SerializedName("humidity")
    var humidity: Double = 0.0) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Main

        if (temp != other.temp) return false
        if (pressure != other.pressure) return false
        if (humidity != other.humidity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = temp.hashCode()
        result = 31 * result + pressure.hashCode()
        result = 31 * result + humidity.hashCode()
        return result
    }

    override fun toString(): String {
        return "Main: temp = $temp, pressure = $pressure, humidity = $humidity"
    }
}