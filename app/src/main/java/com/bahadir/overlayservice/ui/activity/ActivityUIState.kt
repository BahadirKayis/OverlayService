package com.bahadir.overlayservice.ui.activity

import com.bahadir.core.base.State

sealed class ActivityUIState : State {
    data class LoadingState(var status: Boolean = false) : ActivityUIState()
    data class ServiceStatus(val serviceStatus: Boolean) : ActivityUIState()

}