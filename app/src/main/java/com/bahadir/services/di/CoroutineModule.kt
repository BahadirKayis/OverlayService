package com.bahadir.services.di

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
object CoroutineModule {
    @Singleton
    @Provides
    @Named("default")
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Default)

}