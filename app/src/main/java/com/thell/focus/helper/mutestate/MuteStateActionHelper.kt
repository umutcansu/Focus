package com.thell.focus.helper.mutestate

import android.content.Context

class MuteStateActionHelper private constructor() {
    companion object
    {
        private var MuteStateAction : IMuteStateAction? = null

        fun getMuteStateAction(context: Context): IMuteStateAction
        {
            if(MuteStateAction == null)
            {
                MuteStateAction = MuteStateSharedPrefAction(context)
            }
            return MuteStateAction!!
        }
    }
}