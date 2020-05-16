package com.thell.focus.services

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.os.IBinder
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.thell.focus.R
import com.thell.focus.broadcastreceiver.NotificationServiceBroadcastReceiver
import com.thell.focus.helper.bootreceiver.BroadcastReceiverHelper
import com.thell.focus.helper.mutestate.IMuteStateAction
import com.thell.focus.helper.mutestate.MuteStateActionHelper
import com.thell.focus.helper.notificationservice.NotificationServiceHelper
import com.thell.focus.helper.permission.PermissionHelper
import com.thell.focus.ui.activity.MainActivity

class MuteNotificationTileService: TileService()
{

    private lateinit var muteStateAction : IMuteStateAction

    override fun onCreate() {
        super.onCreate()
        muteStateAction = MuteStateActionHelper.getMuteStateAction(this)
    }

    override fun onClick()
    {
        Log.i("tile","onClick")
        super.onClick()
        if(!PermissionHelper.isNotificationServiceEnabled(this))
        {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivityAndCollapse(intent)
        }
        else
        {

            val state = muteStateAction.getMuteState()

            NotificationServiceHelper.setStateService(this,state)

            if (::muteStateAction.isInitialized)
                muteStateAction.switchMuteState()

            setTile()


        }

    }

    override fun onTileRemoved() {

        super.onTileRemoved()
    }

    override fun onDestroy() {

        super.onDestroy()
    }

    override fun onTileAdded() {

        super.onTileAdded()
    }

    override fun onBind(intent: Intent?): IBinder? {

        return super.onBind(intent)
    }


    override fun onStartListening() {
        Log.i("tile","onStartListening")
        super.onStartListening()
        registerReceiver()
        setTile()
    }

    override fun onStopListening() {
        Log.i("tile","onStopListening")
        unregisterReceiver(receiver)
        val tile = qsTile
        tile.apply {
            label =  getString(R.string.loading)
            state = Tile.STATE_UNAVAILABLE
            icon = Icon.createWithResource(this@MuteNotificationTileService, R.drawable.ic_hourglass_full_black_24dp)
            updateTile()
        }
        super.onStopListening()
    }

    private val receiver = object : NotificationServiceBroadcastReceiver()
    {
        override fun onReceive(p0: Context?, p1: Intent?)
        {
            super.onReceive(p0, p1)
            setTile()
        }
    }

    private fun registerReceiver()
    {
        val filter = IntentFilter(BroadcastReceiverHelper.NotificationServiceBroadcastReceiver)
        registerReceiver(receiver, filter)
    }

    fun setTile()
    {
        val tile = qsTile



        tile.apply {
            if(MuteStateActionHelper.getMuteStateAction(this@MuteNotificationTileService).getMuteState())
            {
                label = getString(R.string.mute)
                state = Tile.STATE_ACTIVE
                icon = Icon.createWithResource(this@MuteNotificationTileService, R.drawable.ic_notifications_off_black_24dp)

            }
            else
            {
                label =  getString(R.string.notification)
                state = Tile.STATE_INACTIVE
                icon = Icon.createWithResource(this@MuteNotificationTileService, R.drawable.ic_notifications_black_24dp)
            }

            updateTile()
        }


    }



}