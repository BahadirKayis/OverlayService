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
import com.bahadir.core.domain.repository.Repository
import com.bahadir.service.databinding.OtherAppDesignBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class OverlayService : Service() {
    @Inject
    @Named("default")
    lateinit var coroutineDefault: CoroutineScope

    @Inject
    lateinit var repo: Repository

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
        coroutineDefault.launch {
            repo.setServiceStartTime(Calendar.getInstance().timeInMillis)
        }
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
            LAYOUT_WIDTH,
            LAYOUT_HEIGHT,
            layoutType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
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
        const val LAYOUT_WIDTH = 500
        const val LAYOUT_HEIGHT = 300
        const val CLASS_NAME = "android.intent.action.MAIN"
        const val ACTION = "overlay_package"
        const val PACKAGE_NAME = "package_name"
    }
}