package com.bahadir.services.di

import android.content.Context
import com.bahadir.services.domain.provider.PermissionProvider
import com.bahadir.services.infrastructure.PermissionProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PermissionModule {
    @Provides
    @Singleton
    fun providePermissionManager(@ApplicationContext context: Context): PermissionProvider =
        PermissionProviderImpl(context)
}