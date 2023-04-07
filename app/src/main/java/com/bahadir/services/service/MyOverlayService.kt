package com.bahadir.services.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.bahadir.services.databinding.OtherAppDesignBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyOverlayService : Service() {
    @Inject
    lateinit var usageStateManager: UsageStateManager

    private lateinit var windowManager: WindowManager
    private lateinit var binding: OtherAppDesignBinding
    private lateinit var accessibilityService: MyAccessibilityService
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {

            binding.msg.text =""
        } catch (e: Exception) {
            Log.e("MyOverlayService", " ${e.message}")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        binding = OtherAppDesignBinding.inflate(LayoutInflater.from(this))

        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.RGBA_8888

        )
        layoutParams.gravity = Gravity.TOP or Gravity.START

        accessibilityService = MyAccessibilityService()
        accessibilityService.permisions(this)
        windowManager.addView(binding.root, layoutParams)
    }


    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(binding.root)
    }

    override fun stopService(name: Intent?): Boolean {
        windowManager.removeView(binding.root)
        val intent = Intent(this, MyAccessibilityService::class.java)
        stopService(intent)
        return super.stopService(name)

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.e("onConfigurationChanged", " ${newConfig.orientation}")
        Log.e("onConfiguratnewConfig", " ${newConfig.keyboard}")
    }

}