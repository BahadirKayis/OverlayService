package com.bahadir.services.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.bahadir.services.domain.source.DataStoreDataSource
import kotlinx.coroutines.flow.first

class DataStoreDataSourceImpl(private val context: Context) : DataStoreDataSource {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "com.bahadir.services")
    override suspend fun getServiceStatus(): Boolean {
        return context.datastore.data.first()[booleanPreferencesKey("service_status")] ?: false
    }

    override suspend fun setServiceStatus(status: Boolean) {
        context.datastore.edit {
            it[booleanPreferencesKey("service_status")] = status
        }
    }

}