package com.bahadir.services.domain.provider

interface PermissionProvider {
    fun checkDrawOverlay(): Boolean
    fun checkAccessibilityService(): Boolean
}