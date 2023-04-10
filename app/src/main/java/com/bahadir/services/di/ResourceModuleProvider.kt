package com.bahadir.services.di

import android.content.Context
import com.bahadir.services.data.source.UsageStateDataSourceImpl
import com.bahadir.services.domain.source.UsageStateDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ResourceModuleProvider {
    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): UsageStateDataSource =
        UsageStateDataSourceImpl(context)
}