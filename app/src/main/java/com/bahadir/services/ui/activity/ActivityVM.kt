package com.bahadir.services.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.myanimehero.delegation.viewmodel.VMDelegation
import com.bahadir.myanimehero.delegation.viewmodel.VMDelegationImpl
import com.bahadir.services.common.collectIn
import com.bahadir.services.domain.usecase.GetServiceUseCase
import com.bahadir.services.domain.usecase.SetServiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityVM @Inject constructor(
    private val getServiceStatusUC: GetServiceUseCase,
    private val setServiceStatusUseCase: SetServiceUseCase
) :
    ViewModel(),
    VMDelegation<ActivityUIEffect, ActivityUIEvent, ActivityUIState> by VMDelegationImpl(
        ActivityUIState.LoadingState()
    ) {


    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->
            when (event) {
                is ActivityUIEvent.ServiceStatusChanged -> {
                    setServiceStatus(event.status)
                }
            }
        }
        getServiceStatus()
    }

    private fun setServiceStatus(status: Boolean) = viewModelScope.launch {
        setServiceStatusUseCase.invoke(status)
        getServiceStatus()
    }

    private fun getServiceStatus() {
        getServiceStatusUC.invoke().onEach { result ->
            setState(ActivityUIState.ServiceStatus(result))
        }.launchIn(viewModelScope)
    }

}
