package com.bahadir.overlayservice.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bahadir.core.common.collectIn
import com.bahadir.core.common.showCustomSnackBar
import com.bahadir.core.common.viewBinding
import com.bahadir.core.common.visible
import com.bahadir.overlayservice.R
import com.bahadir.overlayservice.databinding.ActivityMainBinding
import com.bahadir.service.OverlayService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: ActivityVM by viewModels()
    private var serviceStatus: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)
        initUIEffect()
        initUIEvent()
        initUIState()
    }
    private fun initUIEvent() {
        binding.btnServiceControl.setOnClickListener {
            when (serviceStatus) {
                true -> {
                    binding.btnServiceControl.setText(R.string.stop_service)
                }
                false -> {
                    binding.btnServiceControl.setText(R.string.start_service)
                }
            }
            viewModel.setEvent(ActivityUIEvent.ServiceStatusChanged(!serviceStatus))
        }
    }
    private fun initUIState() = viewModel.state.collectIn(this) { state ->
        when (state) {
            is ActivityUIState.ServiceStatus -> {
                if (state.serviceStatus) binding.btnServiceControl.text =
                    getString(R.string.stop_service)
                else binding.btnServiceControl.text = getString(R.string.start_service)

                serviceStatus = state.serviceStatus
            }
            is ActivityUIState.AppUsageTime -> {
                binding.rcAppTime.visible()
                binding.rcAppTime.adapter = AppsUsageTimeAdapter(state.appUsageTime)
            }
            else -> {}
        }
    }
    private fun initUIEffect() = viewModel.effect.collectIn(this) { effect ->
        val svc = Intent(this, OverlayService::class.java)
        when (effect) {
            is ActivityUIEffect.ActionDrawOtherApp -> {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivity(intent)
            }
            is ActivityUIEffect.ActionUsageAccessSettings -> {
                val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                startActivity(intent)
            }
            is ActivityUIEffect.ActionAccessibilityService -> {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(intent)
            }
            is ActivityUIEffect.StartOverlayService -> {
                startService(svc)
            }
            is ActivityUIEffect.StopOverlayService -> {
                stopService(svc)
            }
            is ActivityUIEffect.ShowError -> {
                binding.root.showCustomSnackBar(effect.message)
            }
        }
    }

//    private val registerForActivityResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
//
//        }
}