package com.thell.focus.services

import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.thell.focus.broadcastreceiver.NotificationServiceBroadcastReceiver
import com.thell.focus.helper.mutestate.MuteStateSharedPrefAction
import com.thell.focus.helper.settings.Settings
import com.thell.focus.helper.settings.SettingsHelper


class MuteNotificationListenerService : NotificationListenerService()
{

    private val TAG = "MuteNotificationListenerService"

    override fun onStart(intent: Intent?, startId: Int) {


        registerMuteStateReceiver()
        Log.e(TAG,"onStart")
        super.onStart(intent, startId)
    }


    override fun onDestroy() {
        super.onDestroy()

        try {
            unregisterReceiver(receiverMuteState)
        } catch (e: Exception) {
        }
        Log.e(TAG,"onDestroy")
    }

    private val receiverMuteState = object : NotificationServiceBroadcastReceiver()
    {
        override fun onReceive(p0: Context?, p1: Intent?)
        {
            super.onReceive(p0, p1)
            val state = p1!!.getBooleanExtra(MuteStateSharedPrefAction.MUTE_STATE_KEY,false)
            if(!state)
            {
                if(SettingsHelper.savedAlways.State !=  Settings.StateType.OK)
                {

                }
            }

        }
    }

    private fun registerMuteStateReceiver()
    {

    }

    override fun onBind(intent: Intent?): IBinder?
    {
        return super.onBind(intent)
    }




    private fun muteAction(sbn: StatusBarNotification)
    {
        cancelAllNotifications()

    }

    private fun notificationAction(sbn: StatusBarNotification)
    {

    }

    override fun onNotificationPosted(sbn: StatusBarNotification)
    {


    }

    override fun onNotificationRemoved(sbn: StatusBarNotification)
    {

    }

}