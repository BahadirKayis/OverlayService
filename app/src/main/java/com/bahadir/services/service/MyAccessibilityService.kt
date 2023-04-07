package com.bahadir.services.service

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager


class MyAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName
            Log.d("AccessibilityService: ", "Package name: $packageName")
            // Overlay içindeki metne paket adını dahil etmek için burada gerekli işlemleri yapabilirsiniz.
        }
        Log.e("AccessibilityService: packageName", " ${packageName.toString()}")
        Log.e("AccessibilityService: contentChangeTypes", " ${event.contentChangeTypes}")
        val packageName = event.packageName?.toString()
        if (packageName != null) {
            Log.d("MyAccessibilityService", "Package name: $packageName")
        }
    }

    override fun onCreate() {
        Log.e("AccessibilityService: ", "Created")
        super.onCreate()
    }

    fun permisions(context: Context) {
        val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        if (!am.isEnabled) {
            // Erişilebilirlik hizmeti etkinleştirilmediyse, kullanıcıyı etkinleştirmesi için yönlendirin.
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } else {
            // Erişilebilirlik hizmeti etkinleştirildiyse, servisi kaydettirin.
            val intent = Intent(context, MyAccessibilityService::class.java)
            context.startService(intent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("AccessibilityService: ", "StartedCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onServiceConnected() {
        Log.e("AccessibilityService: ", "Connected")
        super.onServiceConnected()
    }

    override fun stopService(name: Intent?): Boolean {
        return super.stopService(name)
    }

    override fun onInterrupt() {
        // İnterrupt durumunda yapılacak işlem
        Log.e("AccessibilityService: ", "Interrupted")
    }

}