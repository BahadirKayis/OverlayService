package com.bahadir.core.common


import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import com.bahadir.core.R
import com.bahadir.core.databinding.CustomSnackbarBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

fun Long.timeSinceDate(context: Context): String {
    val hour = 60
    val startTime = (Calendar.getInstance().timeInMillis - this) / 1000
    val currentDate = Calendar.getInstance().timeInMillis / 1000
    val difference = (currentDate - startTime)

    return if (difference < hour) {
        context.resources.getString(R.string.second, difference)
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

fun View.visible() {
    this.visibility = View.VISIBLE
}


fun Long.convertMinute(): Int {
    val startTime = (Calendar.getInstance().timeInMillis - this) / 1000
    val currentDate = Calendar.getInstance().timeInMillis / 1000
    val difference = (currentDate - startTime)

    return if (difference < 120) {
        1
    } else {
        (difference / 60).toInt()
    }

}

fun View.showCustomSnackBar(message: String) {
    val snackView = View.inflate(this.context, R.layout.custom_snackbar, null)
    val binding = CustomSnackbarBinding.bind(snackView)
    val snackBar = Snackbar.make(this, "", Snackbar.LENGTH_LONG)
    snackBar.apply {
        setBackgroundTint(resources.color(R.color.red_error_bg))
        (view as ViewGroup).addView(binding.root)
        binding.textMessage.text = message
        show()
    }
}

fun Resources.color(@ColorRes color: Int): Int {
    return ResourcesCompat.getColor(this, color, null)
}
