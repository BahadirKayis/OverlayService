package com.bahadir.core.infrastructure


import android.accessibilityservice.AccessibilityServiceInfo
import android.app.AppOpsManager
import android.content.Context
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import com.bahadir.accessibilityservice.OverlayAccessibilityService
import com.bahadir.core.domain.provider.PermissionProvider

class PermissionProviderImpl(private val context: Context) :
    PermissionProvider {
    override fun checkDrawOverlay(): Boolean {
        //Diğer uygulamaların üzerine çizim yapabilmek için kullanıyorum
        return Settings.canDrawOverlays(context)
    }

    override fun checkAccessibilityService(): Boolean {
        //ACCESSIBILITY_SERVICE izin verilen uygulamaları alıp bu uygulama var mı kontrol ediyorum
        //Ekranda olan uygulamların ismini almak için kullanıyorum
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

    override fun checkUsageStats(): Boolean {
        //Diğer uygulamaların kullanım istatistiklerini alabilmek için kullanıyorum
        val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                context.packageName
            )
        } else {
            appOpsManager.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                context.packageName
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED


    }
}