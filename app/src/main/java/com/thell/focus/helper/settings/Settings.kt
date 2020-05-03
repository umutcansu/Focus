package com.thell.focus.helper.settings

object Settings {

    object StateType
    {
        const val NA = -1
        const val NOK = 0
        const val OK = 1
    }

    class Key private constructor()
    {
        companion object
        {
            const val   SETTINGS_KEY_IS_NOTIFICATION_SAVED_ALWAYS =
                "Would you like to save notifications to history  while notifications are allowed?\nWarning : it may drain your phone battery."
            const val   SETTINGS_KEY_IS_MUTE_NOTIFICATION_TOAST =
                "Would you like to receive toast messages?"
        }
    }
}
