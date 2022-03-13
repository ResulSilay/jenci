package com.x.presentation.util

import com.x.presentation.R

object JenkinsExtension {

    fun Int?.scoreToDrawableId(): Int {
        when {
            this == null -> {
                return R.drawable.ic_weather_icons_sunny
            }
            this >= 80 -> {
                return R.drawable.ic_weather_icons_sunny
            }
            this in 60..79 -> {
                return R.drawable.ic_weather_icons_cloudy
            }
            this in 40..59 -> {
                return R.drawable.ic_weather_icons_overcast
            }
            this in 20..39 -> {
                return R.drawable.ic_weather_icons_rain
            }
            this in 0..19 -> {
                return R.drawable.ic_weather_icons_storm
            }
            else -> {
                return R.drawable.ic_weather_icons_sunny
            }
        }
    }
}