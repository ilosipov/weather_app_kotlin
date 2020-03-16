package com.job4j.weather_app.activity

import androidx.fragment.app.Fragment
import com.job4j.weather_app.BaseActivity
import com.job4j.weather_app.fragment.MainFragment

/**
 * Класс MainActivity - активити реализует абстрактный метод и запускает главный экран
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 16.03.2020
 * @version $Id$
 */

class MainActivity : BaseActivity() {

    override fun createFragment(): Fragment {
        return MainFragment()
    }
}
