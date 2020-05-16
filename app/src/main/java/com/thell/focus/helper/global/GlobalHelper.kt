package com.thell.focus.helper.global

import android.content.Context
import android.content.Intent
import com.thell.focus.FocusApplication
import com.thell.focus.helper.mutestate.MuteStateActionHelper
import com.thell.focus.helper.notificationservice.NotificationServiceHelper
import com.thell.focus.helper.settings.SettingsHelper
import com.thell.focus.services.MuteNotificationTileService


object GlobalHelper
{
    const val   VERSION = "0.0.1"
    const val   DATABASE_NAME = "FocusDatabase"
    const val   DATABASE_VERSION = 1

    fun startApplication(context: Context)
    {
        FocusApplication.getDatabase()
        SettingsHelper.loadSettings()
        startTileService(context)
        val muteStateAction = MuteStateActionHelper.getMuteStateAction(context)
        val state = muteStateAction.getMuteState()
        NotificationServiceHelper.setStateService(context,state)
    }

    private fun startTileService(context: Context)
    {
        val muteNotificationTileService = Intent(context, MuteNotificationTileService::class.java)
        context.startService(muteNotificationTileService)
    }
}