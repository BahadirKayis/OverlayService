package com.bahadir.core.data.repository

import com.bahadir.core.common.Resource
import com.bahadir.core.domain.model.UsageStateUI
import com.bahadir.core.domain.repository.Repository
import com.bahadir.core.domain.source.DataStoreDataSource
import com.bahadir.core.domain.source.UsageStateDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RepositoryImpl(
    private val dataStore: DataStoreDataSource,
    private val usageState: UsageStateDataSource
) : Repository {
    override suspend fun setServiceStatus(status: Boolean) {
        dataStore.setServiceStatus(status)
    }

    override suspend fun getServiceStatus(): Boolean  = dataStore.getServiceStatus()


    override suspend fun setServiceStartTime(startTime: Long) {
        dataStore.setServiceStartTime(startTime)
    }

    override fun getUsageStatesTime(): Flow<Resource<List<UsageStateUI>>> = callbackFlow {
        val startTime = dataStore.getServiceStartTime()

            val data = usageState.getUsageStatesTime(startTime)
            trySend(Resource.Success(data))

        awaitClose { channel.close() }
    }
}