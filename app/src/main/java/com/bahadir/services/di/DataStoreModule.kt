package com.bahadir.services.di

import android.content.Context
import com.bahadir.services.data.source.DataStoreDataSourceImpl
import com.bahadir.services.domain.source.DataStoreDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) : DataStoreDataSource =
        DataStoreDataSourceImpl(context)
}