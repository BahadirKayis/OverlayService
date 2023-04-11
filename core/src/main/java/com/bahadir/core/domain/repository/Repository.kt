package com.bahadir.core.domain.repository

import com.bahadir.core.common.Resource
import com.bahadir.core.domain.model.UsageStateUI
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun setServiceStatus(status: Boolean)
    fun getServiceStatus(): Flow<Boolean>
    suspend fun setServiceStartTime(startTime: Long)
    fun getUsageStatesTime(): Flow<Resource<List<UsageStateUI>>>
}