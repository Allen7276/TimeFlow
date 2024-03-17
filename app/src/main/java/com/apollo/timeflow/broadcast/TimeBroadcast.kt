package com.apollo.timeflow.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimeBroadcast(private val updateTime: () -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        updateTime.invoke()
    }
}