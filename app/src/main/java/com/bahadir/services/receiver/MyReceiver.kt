package com.bahadir.services.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bahadir.services.domain.source.DataStoreDataSource
import com.bahadir.services.service.MyOverlayService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyReceiver : BroadcastReceiver() {

    @Inject
    lateinit var dataStoreDataSource: DataStoreDataSource


    override fun onReceive(context: Context?, intent: Intent?) {
        val sh = context?.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sh?.edit()
        editor?.putString("MyString", "MayMayMay")?.apply()
        Log.e("MyOverlayService", " ${context?.packageName}")
        try {
            CoroutineScope(Dispatchers.Default).launch {
                if (dataStoreDataSource.getServiceStatus()) {
                    if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
                        editor?.putString("MyString", "TRUESE")?.apply()
                        val serviceIntent = Intent(context, MyOverlayService::class.java)
                        context?.startService(serviceIntent)
                    }
                } else {
                    editor?.putString("MyString", "ELSEE")?.apply()
                }

            }
        } catch (e: Exception) {
            editor?.putString("MyString", "Catch:${e.message.toString()}")?.apply()
        }
        editor?.putString("ACTION_PACKAGE_CHANGED", "Catch:${intent?.action == Intent.ACTION_PACKAGE_CHANGED}")?.apply()
        editor?.putString("ACTION_PACKAGE_ADDED", "Catch:${intent?.action == Intent.ACTION_PACKAGE_ADDED}")?.apply()


    }
}
