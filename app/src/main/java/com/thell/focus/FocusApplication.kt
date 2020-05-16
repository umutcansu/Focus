package com.thell.focus

import android.app.Application
import com.thell.focus.database.AppDatabase
import com.thell.focus.helper.global.GlobalHelper

class FocusApplication : Application()
{
    init {
        application = this

    }

    override fun onCreate() {
        super.onCreate()
        GlobalHelper.startApplication(applicationContext)
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