package com.thell.focus.helper.notificationservice
import android.content.Context
import android.content.Intent
import com.thell.focus.helper.mutestate.MuteStateActionHelper
import com.thell.focus.helper.permission.PermissionHelper
import com.thell.focus.ui.activity.MainActivity

class NotificationServiceHelper private constructor()
{
    companion object
    {
        var muteNotificationService: Intent? = null

        var SERVICE_IS_RUNNIG = false

        fun setStateService(context: Context,state:Boolean)
        {
            if(!SERVICE_IS_RUNNIG)
            {
                if(state)
                {
                    if (MuteStateActionHelper.getMuteStateAction(context).getMuteState()) {
                        start(context)
                    }
                }
            }

        }

        private fun start(context: Context)
        {

            if(!PermissionHelper.isNotificationServiceEnabled(context))
            {
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
            else
            {
                startNotificationListenerService(
                    context
                )
            }
        }


        private fun startNotificationListenerService(context: Context)
        {
            if(muteNotificationService != null)
            {
                context.startService(muteNotificationService)
            }

        }

    }
}