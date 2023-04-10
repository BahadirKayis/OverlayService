package com.bahadir.services.domain.mapper

import android.app.usage.UsageStats
import android.content.Context
import com.bahadir.services.common.timeSinceDate
import com.bahadir.services.domain.model.UsageStateUI

fun List<UsageStats>.mapToUsageStateUI(context: Context) = map {
    UsageStateUI(
        appName = it.packageName,
        usageTime = it.totalTimeInForeground.timeSinceDate(context)
    )
}