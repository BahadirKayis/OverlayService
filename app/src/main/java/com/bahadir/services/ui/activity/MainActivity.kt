package com.bahadir.services.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bahadir.services.R
import com.bahadir.services.common.collectIn
import com.bahadir.services.common.viewBinding
import com.bahadir.services.databinding.ActivityMainBinding
import com.bahadir.services.service.OverlayService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: ActivityVM by viewModels()
    private var serviceStatus: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        }
    }

//    private val registerForActivityResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            viewModel.setEvent(ActivityUIEvent.ServiceStatusChanged(!serviceStatus))
//            //ViewModel de kontrol ettiğim için burada kontrol etmeme gerek yok burada ki kodun amacı
//            //Accessibility izin verdikten sonra otomatik olarak başladığı için bilgileri güncellemek
//            için kullanıyorum
//        }
}