package com.aseane.timeflow.lifecycleUtils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object LifecycleUtils {
    fun LifecycleOwner.quickStartCoroutineScope(dispatcher: CoroutineDispatcher = Dispatchers.Main, block: suspend CoroutineScope.() -> Unit) {
        this.lifecycleScope.launch(dispatcher) {
            this@quickStartCoroutineScope.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }
}