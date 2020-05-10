package com.thell.focus.helper.global

import android.content.Context
import com.thell.focus.FocusApplication
import com.thell.focus.helper.settings.SettingsHelper


object GlobalHelper
{
    const val   VERSION = "0.0.1"
    const val   DATABASE_NAME = "FocusDatabase"
    const val   DATABASE_VERSION = 1

    fun startApplication()
    {
        FocusApplication.getDatabase()
        SettingsHelper.loadSettings()
    }
}