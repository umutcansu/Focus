package com.thell.focus.helper.settings

import com.thell.focus.database.entity.SettingsEntity

class SettingsHelper
{
    companion object
    {
        val savedAlways = SettingsEntity(1,
            Settings.Key.Companion::SETTINGS_KEY_IS_NOTIFICATION_SAVED_ALWAYS.name,
            Settings.Key.SETTINGS_KEY_IS_NOTIFICATION_SAVED_ALWAYS,
            Settings.StateType.NA
        )

        val toastMessage = SettingsEntity(2,
            Settings.Key.Companion::SETTINGS_KEY_IS_MUTE_NOTIFICATION_TOAST.name,
            Settings.Key.SETTINGS_KEY_IS_MUTE_NOTIFICATION_TOAST,
            Settings.StateType.OK
        )
    }
}