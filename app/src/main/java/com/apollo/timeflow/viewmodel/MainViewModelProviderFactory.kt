package com.apollo.timeflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apollo.timeflow.MyApplication
import com.apollo.timeflow.service.TimeFormatRecordDataStoreService
import com.apollo.timeflow.service.TimeDataService

class MainViewModelProviderFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        with(modelClass) {
            return when {
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                    TimeFormatRecordDataStoreService.getInstance(
                        MyApplication.instance?.applicationContext
                    ),
                    TimeDataService.getInstance()
                )

                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            } as T
        }
    }
}