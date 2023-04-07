package com.bahadir.services.domain.source

interface DataStoreDataSource {

   suspend fun getServiceStatus(): Boolean

    suspend fun setServiceStatus(status: Boolean)
}