package com.bahadir.services.di

import android.content.Context
import com.bahadir.services.data.repository.RepositoryImpl
import com.bahadir.services.domain.repository.Repository
import com.bahadir.services.domain.source.DataStoreDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(@ApplicationContext context: Context, dataSource: DataStoreDataSource): Repository =
        RepositoryImpl(context, dataSource)
}