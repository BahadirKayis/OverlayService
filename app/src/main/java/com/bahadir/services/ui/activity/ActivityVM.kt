package com.bahadir.services.ui.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.myanimehero.delegation.viewmodel.VMDelegation
import com.bahadir.myanimehero.delegation.viewmodel.VMDelegationImpl
import com.bahadir.services.common.Resource
import com.bahadir.services.common.collectIn
import com.bahadir.services.domain.provider.PermissionProvider
import com.bahadir.services.domain.usecase.GetServiceUseCase
import com.bahadir.services.domain.usecase.GetUsageStateUseCase
import com.bahadir.services.domain.usecase.SetServiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityVM @Inject constructor(
    private val getServiceStatusUC: GetServiceUseCase,
    private val setServiceStatusUseCase: SetServiceUseCase,
    private val permission: PermissionProvider,
    private val usageState: GetUsageStateUseCase
) : ViewModel(),
    VMDelegation<ActivityUIEffect, ActivityUIEvent, ActivityUIState> by VMDelegationImpl(
        ActivityUIState.LoadingState()
    ) {
    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->
            when (event) {
                is ActivityUIEvent.ServiceStatusChanged -> {
                    //her start service dediğinde izinleri tekrar kontrol ediyorum
                    // kullanıcı ayarları gidip kapatmış olabilir
                    if (event.status) checkPermission()
                    else {
                        setEffect(ActivityUIEffect.StopOverlayService)
                        getUsageState()
                        setServiceStatus(event.status)
                    }
                }
            }
        }
        getServiceStatus()
    }

    private fun checkPermission() {
        if (permission.checkUsageStats()) {
            if (permission.checkDrawOverlay()) {
                if (permission.checkAccessibilityService()) {
                    setEffect(ActivityUIEffect.StartOverlayService)
                    setServiceStatus(true)
                } else {
                    setEffect(ActivityUIEffect.ActionAccessibilityService)
                }
            } else {
                setEffect(ActivityUIEffect.ActionDrawOtherApp)
            }
        } else {
            setEffect(ActivityUIEffect.ActionUsageAccessSettings)
        }
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

    private fun getUsageState() {
        usageState().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.i("ActivityVM", "getUsageState: ${result.data}")
                }
                is Resource.Error -> {
                    Log.e("ActivityVM", "getUsageState: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)
    }
}
