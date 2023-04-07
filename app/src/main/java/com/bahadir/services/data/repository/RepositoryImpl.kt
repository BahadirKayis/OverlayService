package com.bahadir.services.data.repository

import android.content.Context
import com.bahadir.services.domain.repository.Repository
import com.bahadir.services.domain.source.DataStoreDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RepositoryImpl(private val context: Context, private val dataStore: DataStoreDataSource) :
    Repository {
    override fun drawPermission() {

    }

    override suspend fun setServiceStatus(status: Boolean) {
        dataStore.setServiceStatus(status)
    }

    override fun getServiceStatus(): Flow<Boolean> = callbackFlow {
        try {
            val status = dataStore.getServiceStatus()
            trySend(status)
        } catch (e: Exception) {
            trySend(false)
        }
        awaitClose { channel.close() }
    }
}