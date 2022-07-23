package com.aseane.timeflow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * 广播是可以处理主线程的UI渲染操作的
 */
class TimeBroadcast(private val viewModel: MainViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        viewModel.updateTime()
    }
}