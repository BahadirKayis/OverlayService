package com.bahadir.services.infrastructure


import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import com.bahadir.services.domain.provider.PermissionProvider
import com.bahadir.services.accessibilty.OverlayAccessibilityService

class PermissionProviderImpl(private val context: Context) : PermissionProvider {
    override fun checkDrawOverlay(): Boolean {
        return Settings.canDrawOverlays(context)
    }

    override fun checkAccessibilityService(): Boolean {
        val accessibilityManager =
            context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val accessibilityServices = accessibilityManager.getEnabledAccessibilityServiceList(
            AccessibilityServiceInfo.FEEDBACK_GENERIC
        )
        for (enabledService in accessibilityServices) {
            val enabledServiceInfo = enabledService.resolveInfo.serviceInfo
            if (enabledServiceInfo.packageName == context.packageName
                && enabledServiceInfo.name == OverlayAccessibilityService::class.java.name
            ) {
                return true
            }
        }
        return false
    }
}