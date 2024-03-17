package com.apollo.timeflow.boardcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DateBroadcast(private val updateDate: () -> Unit): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        updateDate.invoke()
    }
}