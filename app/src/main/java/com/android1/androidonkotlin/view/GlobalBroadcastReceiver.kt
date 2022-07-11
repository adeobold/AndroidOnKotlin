package com.android1.androidonkotlin.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class GlobalBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("@@@", "GlobalBroadcastReceiver")
    }

}