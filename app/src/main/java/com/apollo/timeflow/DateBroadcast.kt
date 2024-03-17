package com.apollo.timeflow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.apollo.timeflow.viewmodel.MainViewModel

class DateBroadcast(private val updateDate: () -> Unit): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        updateDate.invoke()
    }
}