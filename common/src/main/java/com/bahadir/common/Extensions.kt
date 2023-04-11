package com.bahadir.common

import android.content.Context
import com.bahadir.overlayservice.R
import java.util.*
import kotlin.time.Duration.Companion.seconds

fun Long.timeSinceDate(context: Context): String {
    val hour = 60
    val startTime = (Calendar.getInstance().timeInMillis - this) / 1000
    val currentDate = Calendar.getInstance().timeInMillis / 1000
    val difference = (currentDate - startTime)

    return if (difference < hour) {
        context.resources.getString(R.string.second, seconds)
    } else if (difference < hour * hour) {
        val minutes = difference / hour
        context.resources.getString(R.string.minutes, minutes)
    } else if (difference < hour * hour * 24) {
        val hours = difference / (hour * hour)
        context.resources.getString(R.string.hour, hours)
    } else {
        val days = difference / (hour * hour * 24)
        context.resources.getString(R.string.day, days)
    }
}