package com.bahadir.services.ui.activity

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bahadir.services.R
import com.bahadir.services.common.collectIn
import com.bahadir.services.common.viewBinding
import com.bahadir.services.databinding.ActivityMainBinding
import com.bahadir.services.receiver.MyReceiver
import com.bahadir.services.service.MyAccessibilityService
import com.bahadir.services.service.MyOverlayService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: ActivityVM by viewModels()
    private var serviceStatus: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUIEvent()
        initUIState()
        initUIEffect()

        test()

        registerReceiver(MyReceiver(), IntentFilter(Intent.ACTION_BOOT_COMPLETED))

//        val crashButton = Button(this)
//        crashButton.text = "Test Crash"
//        crashButton.setOnClickListener {
//            throw RuntimeException("Test Crash") // Force a crash
//        }
//
//        addContentView(crashButton, ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT))

    }

    private fun initUIEvent() {
        binding.btnServiceControl.setOnClickListener {
            val svc = Intent(this, MyOverlayService::class.java)
            when (serviceStatus) {
                true -> {
                    stopService(svc)
                }
                false -> {
//                    val accessibilityServiceIntent = Intent(this, MyAccessibilityService::class.java)
//                    startService(accessibilityServiceIntent)
//                    startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                    startService(svc)

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

    private fun initUIEffect() = viewModel.effect.collectIn(this) {

    }




    private fun test() {
        if (!Settings.canDrawOverlays(this)) {
            val intent =
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            //startActivityForResult(intent, 12345)
            startActivity(intent)
        }
        val sh = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        Log.e("Tag", "onCreate: ${sh.getString("MyString", "ssqaeq")}")
        Log.e("Tag", "PACKAGE CHANGED: ${sh.getString("PACKAGE CHANGED", "")}")
        Log.e("Tag", "PACKAGE ADDED: ${sh.getString("PACKAGE ADDED", "")}")

    }
}