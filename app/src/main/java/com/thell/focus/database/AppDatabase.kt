package com.thell.focus.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thell.focus.database.dao.NotificationDao
import com.thell.focus.database.dao.SettingsDao
import com.thell.focus.database.entity.NotificationEntity
import com.thell.focus.database.entity.SettingsEntity
import com.thell.focus.helper.global.GlobalHelper
import com.thell.focus.helper.settings.SettingsHelper

@Database(entities = [SettingsEntity::class,NotificationEntity::class],version = GlobalHelper.DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase()
{

    abstract fun getSettingsDao():SettingsDao

    abstract fun getNotificationDao():NotificationDao

    companion object
    {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase
        {
            if(INSTANCE == null)
            {
                INSTANCE =
                    Room.databaseBuilder(context, AppDatabase::class.java,GlobalHelper.DATABASE_NAME).
                        allowMainThreadQueries().
                        fallbackToDestructiveMigration().
                        //addCallback(roomCallback).
                        build()

                val data = INSTANCE!!.getSettingsDao().getAllList()
                if (data.isNullOrEmpty())
                {
                    INSTANCE!!.getSettingsDao().insert(SettingsHelper.savedAlways)
                    INSTANCE!!.getSettingsDao().insert(SettingsHelper.toastMessage)
                }
            }

            return INSTANCE!!
        }

        private val roomCallback=object: RoomDatabase.Callback()
        {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL(seedQuery)
            }

        }

        private val seedQuery = """
            insert into Settings 
            (SettingsKey,SettingsDescription,State)
            values
            (
                "${SettingsHelper.toastMessage.SettingsKey}",
                "${SettingsHelper.toastMessage.SettingsDescription}",
                 ${SettingsHelper.toastMessage.State}
            ),
            (
                "${SettingsHelper.savedAlways.SettingsKey}",
                "${SettingsHelper.savedAlways.SettingsDescription}",
                 ${SettingsHelper.savedAlways.State}
            );
            """.trim()
    }

}
