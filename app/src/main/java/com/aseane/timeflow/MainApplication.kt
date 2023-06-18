package com.aseane.timeflow

import android.app.Application

class MyApplication : Application() {
    companion object {
        private var instance: Application? = null
        fun getGlobalApplication(): Application? {
            return instance
        }
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}