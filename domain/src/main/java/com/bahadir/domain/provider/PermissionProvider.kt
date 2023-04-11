package com.bahadir.overlayservice.domain.provider

interface PermissionProvider {
    fun checkDrawOverlay(): Boolean
    fun checkAccessibilityService(): Boolean
    fun checkUsageStats(): Boolean
}