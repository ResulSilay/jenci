package com.x.presentation.util

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object TimeExtension {

    fun Long?.toAgoTime(): String? {
        val nowTimestamp = System.currentTimeMillis()
        this?.let {
            var time = nowTimestamp - this
            if (nowTimestamp - time < 1000) {
                time += 1000
            }
            return DateUtils.getRelativeTimeSpanString(time, nowTimestamp, DateUtils.SECOND_IN_MILLIS).toString()
        }
        return null
    }

    fun Long?.toAgoDate(): String? {
        val nowTimestamp = System.currentTimeMillis()
        this?.let {
            var time = this
            if (nowTimestamp - time < 1000) {
                time += 1000
            }
            return DateUtils.getRelativeTimeSpanString(time, nowTimestamp, DateUtils.SECOND_IN_MILLIS).toString()
        }
        return null
    }

    fun Long?.toDateFormat(pattern: String = "dd/mm/yyyy hh:mm:ss"): String? {
        if (this == null)
            return null

        val sdf = SimpleDateFormat(pattern, Locale.ROOT)
        val date = Date(this)
        return sdf.format(date)
    }
}