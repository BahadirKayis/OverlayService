package com.bahadir.service.di

import android.content.Context
import com.bahadir.service.watcher.ForegroundAppWatcher
import com.bahadir.service.watcher.ForegroundAppWatcherImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named

@Module
@InstallIn(ServiceComponent::class)
object ForegroundModule {
    @Provides
    @ServiceScoped
    fun provideForegroundAppWatcher(
        @ApplicationContext context: Context,
@Named("MAIN")   coroutineScope: CoroutineScope
    ): ForegroundAppWatcher = ForegroundAppWatcherImpl(context,coroutineScope)
}