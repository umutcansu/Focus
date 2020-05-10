package com.thell.focus.repository.repo

import androidx.lifecycle.LiveData
import com.thell.focus.database.entity.SettingsEntity
import com.thell.focus.repository.util.BaseRepository

class SettingsRepository private  constructor() :BaseRepository<SettingsEntity>()
{

    companion object
    {
        private lateinit var mSettingsRepository: SettingsRepository

        fun getInstance() :SettingsRepository
        {
            if(!Companion::mSettingsRepository.isInitialized)
                mSettingsRepository= SettingsRepository()

            return mSettingsRepository
        }
    }

    val dao = database.getSettingsDao()

    override fun insert(t: SettingsEntity)
    {
        dao.insert(t)
    }

    override fun delete(t: SettingsEntity)
    {
        dao.delete(t)
    }

    override fun update(t: SettingsEntity)
    {
        dao.update(t)
    }

    fun updateByKey(t: SettingsEntity)
    {
        dao.update(t.SettingsKey,t.State)
    }

    override fun getAll(): LiveData<List<SettingsEntity>>
    {
        return dao.getAll()
    }

    override fun getAllList(): List<SettingsEntity> {
        return  dao.getAllList()
    }
}