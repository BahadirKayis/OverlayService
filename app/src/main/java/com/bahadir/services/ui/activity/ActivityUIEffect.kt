package com.bahadir.services.ui.activity

import com.bahadir.services.ui.base.Effect

sealed class ActivityUIEffect : Effect {
    object ActionDrawOtherApp : ActivityUIEffect()
    object ActionAccessibilityService : ActivityUIEffect()
    object StartOverlayService : ActivityUIEffect()
    object StopOverlayService : ActivityUIEffect()
}