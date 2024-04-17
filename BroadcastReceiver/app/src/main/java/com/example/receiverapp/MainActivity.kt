package com.example.receiverapp

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class MainActivity : AppCompatActivity() {

    private val smsReceiver = SmsReceiver()
    private val customReceiver = CustomReceiver()
    private val localReceiver = LocalReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ReceiverApp", "activity created")
        setContentView(R.layout.activity_main)
//        registerReceiver(smsReceiver,
//            IntentFilter("android.provider.Telephony.SMS_RECEIVED")
//        )
//        registerReceiver(customReceiver,
//            IntentFilter("TEST_CUSTOM_ACTION"), Manifest.permission.INTERNET, null, RECEIVER_EXPORTED
//        )
        registerReceiver(customReceiver,
            IntentFilter("TEST_CUSTOM_ACTION"), RECEIVER_EXPORTED
        )
        findViewById<Button>(R.id.btn).setOnClickListener {
            val intent = Intent("TEST_CUSTOM_ACTION_LOCAL")
            LocalBroadcastManager.getInstance(this@MainActivity).sendBroadcast(intent)
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver,
            IntentFilter("TEST_CUSTOM_ACTION_LOCAL")
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ReceiverApp", "activity destroyed")
//        unregisterReceiver(smsReceiver)
        unregisterReceiver(customReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver)
    }
}


/**
 * For sending the broadcast from any application, use sendBroadcast(Intent)
 *
 * Sample example:
 *
 * ```
 * class MainActivity : AppCompatActivity() {
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         setContentView(R.layout.activity_main)
 *         findViewById<Button>(R.id.btn).setOnClickListener {
 *             val intent = Intent("TEST_CUSTOM_ACTION")
 *             intent.putExtra("data", "Some custom data")
 *             sendBroadcast(intent)
 *         }
 *     }
 * }
 * ```
 *
 */