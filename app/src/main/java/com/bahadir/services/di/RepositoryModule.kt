package com.bahadir.services.di

import com.bahadir.services.data.repository.RepositoryImpl
import com.bahadir.services.domain.repository.Repository
import com.bahadir.services.domain.source.DataStoreDataSource
import com.bahadir.services.domain.source.UsageStateDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        dataSource: DataStoreDataSource,
        usageState: UsageStateDataSource
    ): Repository =
        RepositoryImpl(dataSource, usageState)
}