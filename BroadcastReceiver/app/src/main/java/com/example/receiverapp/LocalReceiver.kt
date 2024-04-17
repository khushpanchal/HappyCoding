package com.example.receiverapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class LocalReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "TEST_CUSTOM_ACTION_LOCAL") {
            Log.i("ReceiverApp", "Local Broadcast received")
        }
    }
}