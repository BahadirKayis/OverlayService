package com.bahadir.core.domain.mapper

import android.app.usage.UsageStats
import android.content.Context
import com.bahadir.core.common.timeSinceDate
import com.bahadir.core.domain.model.UsageStateUI

fun List<UsageStats>.mapToUsageStateUI(context: Context) = map {
    UsageStateUI(
        appName = it.packageName,
        usageTime = it.totalTimeInForeground.timeSinceDate(context)
    )
}