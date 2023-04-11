package com.bahadir.overlayservice.domain.repository

import com.bahadir.overlayservice.common.Resource
import com.bahadir.overlayservice.domain.model.UsageStateUI
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun setServiceStatus(status: Boolean)
    fun getServiceStatus(): Flow<Boolean>
    suspend fun setServiceStartTime(startTime: Long)
    fun getUsageStatesTime(): Flow<Resource<List<UsageStateUI>>>
}