package com.bahadir.overlayservice.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bahadir.overlayservice.common.Constants.SERVICE_START_TIME
import com.bahadir.overlayservice.common.Constants.SERVICE_STATUS
import com.bahadir.overlayservice.domain.source.DataStoreDataSource
import kotlinx.coroutines.flow.first

class DataStoreDataSourceImpl(private val context: Context) : DataStoreDataSource {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "com.bahadir.services")
    override suspend fun getServiceStatus(): Boolean {
        return context.datastore.data.first()[booleanPreferencesKey(SERVICE_STATUS)] ?: false
    }

    override suspend fun setServiceStatus(status: Boolean) {
        context.datastore.edit {
            it[booleanPreferencesKey(SERVICE_STATUS)] = status
        }
    }

    override suspend fun getServiceStartTime(): Long {
        return context.datastore.data.first()[longPreferencesKey(SERVICE_START_TIME)] ?: 0
    }

    override suspend fun setServiceStartTime(startTime: Long) {
        context.datastore.edit {
            it[longPreferencesKey(SERVICE_START_TIME)] = startTime
        }
    }

}