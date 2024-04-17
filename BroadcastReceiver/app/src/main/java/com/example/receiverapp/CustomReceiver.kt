package com.example.receiverapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class CustomReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "TEST_CUSTOM_ACTION") {
            val value = intent.extras?.getString("data")
            Log.i("ReceiverApp", "Custom Received: $value")
        }
    }
}