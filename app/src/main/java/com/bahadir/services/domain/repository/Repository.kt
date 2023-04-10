package com.bahadir.services.domain.repository

import android.app.usage.UsageStats
import com.bahadir.services.common.Resource
import com.bahadir.services.domain.model.UsageStateUI
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun setServiceStatus(status: Boolean)
    fun getServiceStatus(): Flow<Boolean>
    suspend fun setServiceStartTime(startTime: Long)
    fun getUsageStatesTime(): Flow<Resource<List<UsageStateUI>>>
}