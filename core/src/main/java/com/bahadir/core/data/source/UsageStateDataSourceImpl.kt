package com.bahadir.core.data.source

import android.app.usage.UsageStatsManager
import android.content.Context
import com.bahadir.core.domain.mapper.mapToUsageStateUI
import com.bahadir.core.domain.model.UsageStateUI
import com.bahadir.core.domain.source.UsageStateDataSource
import java.util.*

class UsageStateDataSourceImpl(private val context: Context) : UsageStateDataSource {
    override fun getUsageStatesTime(startTime: Long): List<UsageStateUI> {
        val usageStatsManager: UsageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        //Her ne kadar yavaş ve pil tüketimi yüksek olsa da
        // Doğruluk payı önemli olduğu için INTERVAL_BEST kullanıyorum

        val calendarEnd = Calendar.getInstance().timeInMillis
        val data = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_BEST, startTime, calendarEnd
        )
        return data.filter { usageStats ->
            usageStats.totalTimeInForeground > 0 && usageStats.firstTimeStamp in startTime..calendarEnd
        }.mapToUsageStateUI(context)
    }
}