package com.bahadir.services.accessibilty

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.view.accessibility.AccessibilityEvent


class OverlayAccessibilityService : AccessibilityService() {
    override fun onServiceConnected() {
        super.onServiceConnected()
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
        val intent = Intent("overlay_package")
        intent.putExtra("package_name", text)
        sendBroadcast(intent)
    }

    override fun onInterrupt() {
        //Servis kesintiye uğradığında çalışır
    }

    companion object {
        const val NOTIFICATION_TIMEOUT = 100L
    }

}