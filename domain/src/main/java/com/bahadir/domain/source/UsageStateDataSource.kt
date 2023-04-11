package com.bahadir.overlayservice.domain.source

import com.bahadir.overlayservice.domain.model.UsageStateUI

interface UsageStateDataSource {
    fun getUsageStatesTime(startTime: Long): List<UsageStateUI>

}