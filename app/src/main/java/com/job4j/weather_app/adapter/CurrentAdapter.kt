package com.job4j.weather_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.job4j.weather_app.R

/**
 * Класс CurrentAdapter - адаптер текущей информации
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 17.03.2020
 * @version $Id$
 */

class CurrentAdapter(var context: Context, var resource: Int, var currentInfo: Map<String, String>) :
    RecyclerView.Adapter<CurrentAdapter.CurrentViewHolder>() {
    private var keyArray = arrayListOf<String>()
    private var valueArray = arrayListOf<String>()

    init {
        for (info in currentInfo) {
            keyArray.add(info.key)
            valueArray.add(info.value)
        }
    }

    class CurrentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CurrentViewHolder {
        return CurrentViewHolder(LayoutInflater.from(context).inflate(resource, parent, false))
    }

    override fun onBindViewHolder(holder: CurrentViewHolder, position: Int) {
        val key = keyArray[position]
        val value = valueArray[position]

        val infoTitle = holder.itemView.findViewById<TextView>(R.id.current_info_key)
        infoTitle.text = key

        val infoValue = holder.itemView.findViewById<TextView>(R.id.current_info_value)
        infoValue.text = value
    }

    override fun getItemCount(): Int {
        return currentInfo.size
    }

}