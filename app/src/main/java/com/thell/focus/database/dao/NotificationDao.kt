package com.thell.focus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.thell.focus.database.entity.NotificationEntity
import com.thell.focus.database.util.IDatabaseOperation

@Dao
interface NotificationDao: IDatabaseOperation<NotificationEntity>
{

    @Query("select * from Notification order by ID desc")
    override fun getAll(): LiveData<List<NotificationEntity>>

    @Query("select * from Notification order by ID desc")
    override fun getAllList(): List<NotificationEntity>

    @Query("delete from Notification")
    fun deleteAll()

}