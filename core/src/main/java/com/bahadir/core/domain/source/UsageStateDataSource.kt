package com.bahadir.core.domain.source

import android.content.pm.PackageInfo
import com.bahadir.core.domain.model.UsageStateUI

interface UsageStateDataSource {
    fun getUsageStatesTime(startTime: Long): List<UsageStateUI>
    fun isSystemApp(pkgInfo: PackageInfo): Boolean
    fun getInstalledApps(usageState: MutableList<UsageStateUI>): List<UsageStateUI>

}