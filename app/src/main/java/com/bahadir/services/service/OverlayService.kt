package com.bahadir.services.service

import android.app.ActionBar
import android.app.Service
import android.content.BroadcastReceiver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.bahadir.services.R

import com.bahadir.services.data.usage.UsageStateManagerImpl
import com.bahadir.services.databinding.OtherAppDesignBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OverlayService : Service() {
    @Inject
    lateinit var usageStateManagerImpl: UsageStateManagerImpl

    private lateinit var windowManager: WindowManager
    private lateinit var binding: OtherAppDesignBinding
    private var receiverBoot = com.bahadir.services.broadcast.BroadcastReceiver()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    //Bunu yapma sebebim uygulama ismini almam için AccessibilityService lazım
    //ama overlayı da istediğim gibi yönetebilmem için bg service lazım
    private val packageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val packageName = intent.getStringExtra("package_name")
            updateOverlayText(packageName.toString())
        }
    }

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter()
        filter.addAction("overlay_package")
        registerReceiver(packageReceiver, filter)
        registerReceiver(receiverBoot, IntentFilter(Intent.ACTION_BOOT_COMPLETED))
        createOverlay()
    }

    private fun createOverlay() {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        binding = OtherAppDesignBinding.inflate(LayoutInflater.from(this))
        val layoutType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        val layoutParams = WindowManager.LayoutParams(
            300,
            ActionBar.LayoutParams.WRAP_CONTENT,
            layoutType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        binding.button.setOnClickListener {

        }
        layoutParams.gravity = Gravity.TOP or Gravity.CENTER
        windowManager.addView(binding.root, layoutParams)
    }

    private fun updateOverlayText(text: String) {
        binding.msg.text = this.getString(R.string.app_package_name, text)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(packageReceiver)
        unregisterReceiver(receiverBoot)
        windowManager.removeView(binding.root)
    }


}