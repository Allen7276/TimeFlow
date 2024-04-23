package com.apollo.timeflow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.apollo.timeflow.viewmodel.TimeDataViewModel

class DateBroadcast(private val viewModel: TimeDataViewModel): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        viewModel.updateDate()
    }
}