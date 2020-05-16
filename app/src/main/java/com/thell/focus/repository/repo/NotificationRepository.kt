package com.thell.focus.repository.repo

import androidx.lifecycle.LiveData
import com.thell.focus.database.entity.NotificationEntity
import com.thell.focus.repository.util.BaseRepository

class NotificationRepository  private  constructor() : BaseRepository<NotificationEntity>()
{

    companion object
    {
        private lateinit var mNotificationRepository: NotificationRepository

        fun getInstance() :NotificationRepository
        {
            if(!Companion::mNotificationRepository.isInitialized)
                mNotificationRepository= NotificationRepository()

            return mNotificationRepository
        }
    }

    val dao = database.getNotificationDao()

    override fun insert(t: NotificationEntity)
    {
        dao.insert(t)
    }

    override fun delete(t: NotificationEntity)
    {
        dao.delete(t)
    }

    override fun update(t: NotificationEntity)
    {
        dao.update(t)
    }


    override fun getAll(): LiveData<List<NotificationEntity>>
    {
        return dao.getAll()
    }

    override fun getAllList(): List<NotificationEntity> {
        return  dao.getAllList()
    }
}