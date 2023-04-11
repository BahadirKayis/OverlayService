package com.bahadir.overlayservice.data.repository

import android.util.Log
import com.bahadir.overlayservice.common.Resource
import com.bahadir.overlayservice.domain.model.UsageStateUI
import com.bahadir.overlayservice.domain.repository.Repository
import com.bahadir.overlayservice.domain.source.DataStoreDataSource
import com.bahadir.overlayservice.domain.source.UsageStateDataSource
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

    override fun getServiceStatus(): Flow<Boolean> = callbackFlow {
        val status = dataStore.getServiceStatus()
        trySend(status)
        awaitClose { channel.close() }
    }


    override suspend fun setServiceStartTime(startTime: Long) {
        dataStore.setServiceStartTime(startTime)
    }

    override fun getUsageStatesTime(): Flow<Resource<List<UsageStateUI>>> = callbackFlow {
        val startTime = dataStore.getServiceStartTime()
        try {
            Log.e("OverlayService", "getUsageStatesTime: $startTime")
            val data = usageState.getUsageStatesTime(startTime)
            Log.e("OverlayService", "DAAA: $data")
            trySend(Resource.Success(data))

        } catch (e: Exception) {
            trySend(Resource.Error(e.message.toString()))
        }

        awaitClose { channel.close() }
    }
}