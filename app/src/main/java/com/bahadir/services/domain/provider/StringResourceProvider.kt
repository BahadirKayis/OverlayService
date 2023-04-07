package com.bahadir.services.domain.provider

interface StringResourceProvider {
    fun getString(stringResId: Int): String
}