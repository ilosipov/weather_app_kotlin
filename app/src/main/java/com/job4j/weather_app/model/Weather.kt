package com.job4j.weather_app.model

/**
 * Класс Weather - модель описания погоды
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 16.03.2020
 * @version $Id$
 */

data class Weather(
    var main: String = "",
    var description: String = "",
    var icon: String = "") {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Weather

        if (main != other.main) return false
        if (description != other.description) return false
        if (icon != other.icon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = main.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + icon.hashCode()
        return result
    }

    override fun toString(): String {
        return "Weather: main = $main, description = $description, icon = $icon"
    }
}