package com.job4j.weather_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.job4j.weather_app.R
import com.job4j.weather_app.model.Day
import java.text.SimpleDateFormat
import java.util.*

/**
 * Класс ForecastAdapter - адаптер прогноза погода на 5 дней
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 19.03.2020
 * @version $Id$
 */

class ForecastAdapter(var context: Context, var resource: Int, private val days: List<Day>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ForecastViewHolder {
        return ForecastViewHolder(LayoutInflater.from(context).inflate(resource, parent, false))
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val day = days[position]

        val date = holder.itemView.findViewById<TextView>(R.id.forecast_date)
        date.text = setDateToConvert(day.dt_txt)

        val description = holder.itemView.findViewById<TextView>(R.id.forecast_description)
        description.text = day.weather!![0].description.trim()

        val icon = holder.itemView.findViewById<LottieAnimationView>(R.id.forecast_icon)
        icon.setAnimation("${day.weather!![0].icon}.json")

        val temp = holder.itemView.findViewById<TextView>(R.id.forecast_temp)
        temp.text = String.format("%s°", day.main!!.temp.toInt())
    }

    override fun getItemCount(): Int {
        return days.size
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun setDateToConvert(date: String) : String {
        return SimpleDateFormat("dd.MM.yy HH:mm", Locale.ENGLISH).format(
            SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.ENGLISH).parse(date))
    }
}