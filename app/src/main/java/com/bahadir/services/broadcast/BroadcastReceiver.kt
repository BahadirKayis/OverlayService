package com.bahadir.services.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bahadir.services.domain.source.DataStoreDataSource
import com.bahadir.services.service.OverlayService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var dataStoreDataSource: DataStoreDataSource
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, OverlayService::class.java)
            context?.startService(serviceIntent)
        }
    }

}
