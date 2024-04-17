package com.example.receiverapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SmsReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.provider.Telephony.SMS_RECEIVED") {
            Log.i("ReceiverApp", "SMS Received")
        }
    }
}