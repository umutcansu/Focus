package com.thell.focus

import android.app.Application
import com.thell.focus.database.AppDatabase

class FocusApplication : Application()
{
    init {
        application = this
    }

    companion object
    {
        private var application: FocusApplication? = null
        private var INSTANCE: AppDatabase? = null

        fun getDatabase():AppDatabase
        {
            return INSTANCE ?: AppDatabase.getInstance(application!!)
        }

    }

}