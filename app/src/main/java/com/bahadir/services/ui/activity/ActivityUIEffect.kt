package com.bahadir.services.ui.activity

import com.bahadir.services.ui.base.Effect

sealed class ActivityUIEffect : Effect {
    data class ShowError(val message: String) : ActivityUIEffect()
}