package com.x.presentation.util

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import com.squareup.seismic.ShakeDetector
import com.x.presentation.BuildConfig

object ShakeUtil {

    fun Context.shakeDetectorInit(listener: ShakeDetector.Listener) {
        if (!BuildConfig.DEBUG)
            return

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager?
        val shakeDetector = ShakeDetector(listener)
        val sensorDelay = SensorManager.SENSOR_DELAY_GAME
        shakeDetector.start(sensorManager, sensorDelay)
    }
}