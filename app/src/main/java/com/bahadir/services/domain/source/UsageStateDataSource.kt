package com.bahadir.services.domain.source

import android.app.usage.UsageStats
import com.bahadir.services.domain.model.UsageStateUI

interface UsageStateDataSource {
    fun getUsageStatesTime(startTime: Long): List<UsageStateUI>

}