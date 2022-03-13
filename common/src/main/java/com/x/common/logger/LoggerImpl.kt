package com.x.common.logger

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber

class LoggerImpl : Logger {

    inner class TimberTree : Timber.DebugTree() {
        @SuppressLint("LogNotTimber")
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            when (priority) {
                Log.ERROR -> {
                    Log.e(tag, message)
                }
                else -> {
                    Log.d(tag, message)
                }
            }
        }
    }

    override fun init() {
        Timber.uprootAll()
        Timber.plant(TimberTree())
    }

    override fun e(t: Throwable?) {
        Timber.tag(TAG).e(t)
    }

    override fun log(log: String?) {
        Timber.tag(TAG).d(log)
    }

    companion object {
        private const val TAG = "Logger"
    }
}