package com.bahadir.core.domain.provider

interface PermissionProvider {
    fun checkDrawOverlay(): Boolean
    fun checkAccessibilityService(): Boolean
    fun checkUsageStats(): Boolean
}