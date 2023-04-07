package com.bahadir.services.domain.repository

import kotlinx.coroutines.flow.Flow

interface Repository {
    fun drawPermission()
    suspend fun setServiceStatus(status: Boolean)
    fun getServiceStatus():Flow<Boolean>
}