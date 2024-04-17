package com.example.receiverapp

import android.app.Application
import android.util.Log

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i("ReceiverApp", "application created")
    }
}