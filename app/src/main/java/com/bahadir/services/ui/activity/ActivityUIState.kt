package com.bahadir.services.ui.activity

import com.bahadir.services.ui.base.State

sealed class ActivityUIState : State {
    data class LoadingState(var status: Boolean = false) : ActivityUIState()
    data class ServiceStatus(val serviceStatus: Boolean) : ActivityUIState()

}