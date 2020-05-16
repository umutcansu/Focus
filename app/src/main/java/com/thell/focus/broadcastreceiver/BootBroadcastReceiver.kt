package com.thell.focus.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.thell.focus.helper.global.GlobalHelper

class BootBroadcastReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {
        GlobalHelper.startApplication()
    }

}