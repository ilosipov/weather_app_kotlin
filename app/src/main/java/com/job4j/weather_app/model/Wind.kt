package com.job4j.weather_app.model

import com.google.gson.annotations.SerializedName

/**
 * Класс Wind - модель данных скорости
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 16.03.2020
 * @version $Id$
 */

data class Wind(
    @SerializedName("speed")
    var speed: Double = 0.0) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Wind

        if (speed != other.speed) return false

        return true
    }

    override fun hashCode(): Int {
        return speed.hashCode()
    }

    override fun toString(): String {
        return "Wind: speed = $speed"
    }
}