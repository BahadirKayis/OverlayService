package com.bahadir.service.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesModule {
    @Singleton
    @Provides
    @Named("default")
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Default)
}