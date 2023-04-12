package com.bahadir.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bahadir.core.domain.repository.Repository
import com.bahadir.service.OverlayService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class BootBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repo: Repository
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.Default).launch {
                if (repo.getServiceStatus()) {
                    val serviceIntent = Intent(context, OverlayService::class.java)
                    context?.startService(serviceIntent)
                }
            }

        }
    }

}
