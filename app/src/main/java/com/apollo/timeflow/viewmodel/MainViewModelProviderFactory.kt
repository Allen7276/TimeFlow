package com.apollo.timeflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apollo.timeflow.MyApplication
import com.apollo.timeflow.model.DataStoreModel
import com.apollo.timeflow.model.Model

class MainViewModelProviderFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        with(modelClass) {
            return when {
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                    DataStoreModel.getInstance(
                        MyApplication.instance?.applicationContext
                    ),
                    Model.getInstance()
                )

                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            } as T
        }
    }
}