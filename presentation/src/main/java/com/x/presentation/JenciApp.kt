package com.x.presentation

import android.app.Application
import com.x.common.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import x.exception.lib.CrashHandler
import javax.inject.Inject

@HiltAndroidApp
class JenciApp : Application() {

    @Inject
    lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()

        logger.init()

        CrashHandler.init { _: Long, _: Thread, throwable: Throwable ->
            logger.e(throwable)
        }
    }
}