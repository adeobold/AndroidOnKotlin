package com.android1.androidonkotlin.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.android1.androidonkotlin.utils.isAirplaneModeOn

class GlobalBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        isAirplaneModeOn = intent.getBooleanExtra("state", false)
    }

}