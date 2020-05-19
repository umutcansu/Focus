package com.thell.focus.repository.repo

import androidx.lifecycle.LiveData
import com.thell.focus.database.entity.TimerEntity
import com.thell.focus.repository.util.BaseRepository

class TimerRepository private  constructor() : BaseRepository<TimerEntity>()
{
    val dao = database.getTimerDao()

    companion object
    {
        private lateinit var mTimerRepository: TimerRepository

        fun getInstance() :TimerRepository
        {
            if(!Companion::mTimerRepository.isInitialized)
                mTimerRepository= TimerRepository()

            return mTimerRepository
        }
    }

    override fun insert(t: TimerEntity) {
        dao.insert(t)
    }

    override fun delete(t: TimerEntity) {
        dao.delete(t)
    }

    override fun update(t: TimerEntity) {
        dao.update(t)
    }

    override fun getAll(): LiveData<List<TimerEntity>> {
        return dao.getAll()
    }

    override fun getAllList(): List<TimerEntity> {
        return dao.getAllList()
    }

    fun update(id:Int,isFinish:Boolean)
    {
        dao.update(id,isFinish)
    }

    fun updateAll(isFinish:Boolean)
    {
        dao.updateAll(isFinish)
    }

    fun getLastTimer(state:Boolean = false):TimerEntity
    {
        return dao.getLastTimer(state)
    }

}