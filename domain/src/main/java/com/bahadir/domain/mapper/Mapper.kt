package com.bahadir.overlayservice.domain.mapper

import android.app.usage.UsageStats
import android.content.Context
import com.bahadir.overlayservice.common.timeSinceDate
import com.bahadir.overlayservice.domain.model.UsageStateUI

fun List<UsageStats>.mapToUsageStateUI(context: Context) = map {
    UsageStateUI(
        appName = it.packageName,
        usageTime = it.totalTimeInForeground.timeSinceDate(context)
    )
}