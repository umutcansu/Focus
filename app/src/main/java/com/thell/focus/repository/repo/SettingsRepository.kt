package com.thell.focus.repository.repo

import androidx.lifecycle.LiveData
import com.thell.focus.database.entity.SettingsEntity
import com.thell.focus.repository.util.BaseRepository

class SettingsRepository:BaseRepository<SettingsEntity>()
{
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

    override fun getAll(): LiveData<List<SettingsEntity>>
    {
        return dao.getAll()
    }
}