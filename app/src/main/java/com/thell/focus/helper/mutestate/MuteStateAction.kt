package com.thell.focus.helper.mutestate

import android.content.Context
import android.content.Intent
import com.thell.focus.helper.bootreceiver.BroadcastReceiverHelper

abstract class MuteStateAction(val context: Context):IMuteStateAction
{

    abstract override fun getMuteState():Boolean

    abstract override fun setMuteState(value: Boolean)

    override fun sendBroadcast()
    {
        val intent =  Intent(BroadcastReceiverHelper.NotificationServiceBroadcastReceiver)
        intent.putExtra(MuteStateSharedPrefAction.MUTE_STATE_KEY,getMuteState())
        context.sendBroadcast(intent)
    }



    override fun switchMuteState(): Boolean {
        val currentState = getMuteState()
        val newState = !currentState
        setMuteState(newState)
        return  newState
    }

}