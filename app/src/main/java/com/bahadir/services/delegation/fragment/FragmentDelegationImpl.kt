package com.bahadir.myanimehero.delegation.fragment

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject

class FragmentDelegationImpl @Inject constructor(private val lifecycle: Lifecycle) :
    FragmentDelegation,
    DefaultLifecycleObserver {
    override fun registerLifeCycleLoggerDelegation() {
        Log.e("TAG", "registerLifeCycleLoggerDelegation: ")
        lifecycle.addObserver(this)

    }


    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.e("FragmentDelegationImpl", "onCreate   lifecycle.name")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        println("onDestroy")
        Log.e("FragmentDelegationImpl", "onDestroy")
        lifecycle.removeObserver(this)
    }


    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        println("onDestroy")
        Log.e("FragmentDelegationImpl", "onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        println("onDestroy")
        Log.e("FragmentDelegationImpl", "onStop")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        println("onDestroy")
        Log.e("FragmentDelegationImpl", "onStart")
    }
}