package com.bahadir.accessibilityservice

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.view.accessibility.AccessibilityEvent


class OverlayAccessibilityService : AccessibilityService() {
    override fun onServiceConnected() {
        super.onServiceConnected()
        //Servis bilgilerini yazdığımız yer XML de yazdığım için bırada yazmaya gerek yok
        //Servis bağalndığında event type ekran değişince çalışacak şekilde ayarladım
        val info = AccessibilityServiceInfo()
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.notificationTimeout = NOTIFICATION_TIMEOUT
        this.serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val packageName = event?.packageName?.toString()
        if (!packageName.isNullOrEmpty()) {
            updateOverlayText(packageName)
        }
    }

    private fun updateOverlayText(text: String) {
        // paket ismini alıp broadcast ile servise gönderiyorums
        val intent = Intent(ACTION)
        intent.putExtra(PACKAGE_NAME, text)
        sendBroadcast(intent)
    }

    override fun onInterrupt() {
        //Servis kesintiye uğradığında çalışır
    }

    companion object {
        const val NOTIFICATION_TIMEOUT = 100L
        const val ACTION="overlay_package"
        const val PACKAGE_NAME="package_name"
    }

}