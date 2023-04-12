package com.bahadir.core.di

import com.bahadir.core.data.repository.RepositoryImpl
import com.bahadir.core.domain.repository.Repository
import com.bahadir.core.domain.source.DataStoreDataSource
import com.bahadir.core.domain.source.UsageStateDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun provideRepository(
        dataSource: DataStoreDataSource,
        usageState: UsageStateDataSource
    ): Repository =
        RepositoryImpl(dataSource, usageState)
}