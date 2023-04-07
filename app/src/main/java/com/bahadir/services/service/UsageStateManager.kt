package com.bahadir.services.service

import android.app.usage.UsageEvents
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import java.util.*

class UsageStateManager(private val context: Context) {
    fun getUsageStats() {
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
                Log.e("UsageStateManager", usageStats.packageName + " Time:" + usageStats.totalTimeInForeground)
            }
        }
    }

    fun getApp() {
        val usageStatsManager: UsageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        var foregroundAppPackageName: String? = null
        val currentTime = System.currentTimeMillis()
// The `queryEvents` method takes in the `beginTime` and `endTime` to retrieve the usage events.
// In our case, beginTime = currentTime - 10 minutes ( 1000 * 60 * 10 milliseconds )
// and endTime = currentTime
        val usageEvents = usageStatsManager.queryEvents(currentTime - (1000 * 60 * 10), currentTime)
        val usageEvent = UsageEvents.Event()
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(usageEvent)
            Log.e("APP", "${usageEvent.packageName} ${usageEvent.timeStamp}")
        }
    }


    private fun getInstalledApps(context: Context) {

        val packs = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getInstalledPackages(
                PackageManager.PackageInfoFlags.of(PackageManager.GET_META_DATA.toLong())
            )
        } else {
            context.packageManager.getInstalledPackages(
                PackageManager.GET_META_DATA
            )
        }
        packs.forEach { p ->
            if (!isSystemPackage(p)) {
                val appName =
                    p.applicationInfo.loadLabel(context.packageManager).toString()
                val icon = p.applicationInfo.loadIcon(context.packageManager)
                val packages = p.applicationInfo.packageName
                Log.e("getInstalledApps", " $appName $icon $packages")

            }
        }

    }

    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
}