package com.bahadir.service

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
import android.view.WindowManager.LayoutParams
import com.bahadir.service.databinding.OtherAppDesignBinding


class OverlayService : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var binding: OtherAppDesignBinding

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    //Diğer uygulamların paket ismini almak için AccessibilityService kullanıyorum
    //Overlayı doğru bir şekilde yönetebilmek için de Service kullanıyorum
    // Arasında ki iletişimi sağlamak içn de bunu kullanıyorum
    private val packageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val packageName = intent.getStringExtra(PACKAGE_NAME)
            updateOverlayText(packageName.toString())
        }
    }

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter()
        filter.addAction(ACTION)
        registerReceiver(packageReceiver, filter)
        createOverlay()
    }

    private fun createOverlay() {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        binding = OtherAppDesignBinding.inflate(LayoutInflater.from(this))
        val layoutType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            LayoutParams.TYPE_PHONE
        }
        val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LAYOUT_HEIGHT,
            layoutType,
            LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        layoutParams.gravity = Gravity.TOP or Gravity.CENTER
        windowManager.addView(binding.root, layoutParams)
    }

    private fun updateOverlayText(text: String) {
        binding.textAppName.text = this.getString(R.string.app_package_name, text)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(packageReceiver)
        windowManager.removeView(binding.root)
    }

    companion object {
        const val LAYOUT_HEIGHT = 200
        const val ACTION = "overlay_package"
        const val PACKAGE_NAME = "package_name"
    }

}