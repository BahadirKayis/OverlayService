package com.bahadir.services.provider

import android.content.Context
import com.bahadir.services.domain.provider.StringResourceProvider
import javax.inject.Inject

class StringResourceProviderImpl @Inject constructor(private val context: Context) :
    StringResourceProvider {
    override fun getString(stringResId: Int): String {
        return context.getString(stringResId)
    }

}