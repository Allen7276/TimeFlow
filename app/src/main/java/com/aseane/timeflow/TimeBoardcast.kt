package com.aseane.timeflow

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.aseane.timeflow.Model.updateTime

/**
 * 广播是可以处理主线程的UI渲染操作的
 */
class TimeBoardcast(private val viewModel: MainViewModel) : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        updateTime(viewModel)
    }
}