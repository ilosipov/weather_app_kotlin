package com.job4j.weather_app.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.job4j.weather_app.R

/**
 * Класс ForecastBottomFragment - фрагмент с прогнозом погоды на 5 дней
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 19.03.2020
 * @version $Id$
 */

class ForecastBottomFragment : BottomSheetDialogFragment() {
    private val TAG = "ForecastBottomFragment"

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        Log.d(TAG, "setupDialog: start bottom forecast fragment.")
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_bottom_forecast, null)

        dialog.setContentView(view)
    }


}