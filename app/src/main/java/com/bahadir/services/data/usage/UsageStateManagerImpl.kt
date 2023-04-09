package com.bahadir.services.data.usage

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import com.bahadir.services.domain.usage.UsageStateManager
import java.util.*

class UsageStateManagerImpl(private val context: Context):UsageStateManager {
    fun getUsageStats() {
        //Burada da service başlatıp ve bitinceyi gircez ona göre gelecek
        val usageStatsManager: UsageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val calendar = Calendar.getInstance()
        val calendarEnd = calendar.timeInMillis
        calendar.add(Calendar.DATE, -1)
        val calendarStart = calendar.timeInMillis
        usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            calendarStart,
            calendarEnd
        ).let { usageStatsList ->
            usageStatsList.forEach { usageStats ->
                //ön planda geçirilen süre 0 dan büyükse alıyorum
                if (usageStats.totalTimeInForeground > 0) {
                    Log.e(
                        "UsageStateManager",
                        usageStats.packageName + " Time:" + usageStats.totalTimeInForeground
                    )
                }
            }
        }
    }

    fun getApp() {
        //Bu da başka bir örnek 10 dk yı alıyor şuan ama onu düzeltcem
        val usageStatsManager: UsageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        var foregroundAppPackageName: String? = null
        val currentTime = System.currentTimeMillis()

        val usageEvents = usageStatsManager.queryEvents(currentTime - (1000 * 60 * 10), currentTime)
        val usageEvent = UsageEvents.Event()
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(usageEvent)
            Log.e("APPName", "${usageEvent.packageName} Time: ${usageEvent.timeStamp}")
        }
    }

    override fun getAppsTime() {
        TODO("Not yet implemented")
    }

}