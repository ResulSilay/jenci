package com.x.presentation.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.chuckerteam.chucker.api.Chucker
import com.squareup.seismic.ShakeDetector
import com.x.domain.repository.cache.CachePreferences
import com.x.presentation.util.ShakeUtil.shakeDetectorInit
import com.x.presentation.util.start
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : ComponentActivity(), ShakeDetector.Listener {

    @Inject
    lateinit var cachePreferences: CachePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shakeDetectorInit(this)
    }

    override fun hearShake() {
        launchChucker()
    }

    private fun launchChucker() {
        start(intent = Chucker.getLaunchIntent(this))
    }

    fun cacheManager(): CachePreferences = cachePreferences
}