package com.aseane.timeflow

import android.app.Application

class MyApplication : Application() {
    companion object {
        var instance: Application? = null
            private set
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}