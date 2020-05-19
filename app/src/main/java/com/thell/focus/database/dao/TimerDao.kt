package com.thell.focus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.thell.focus.database.entity.SettingsEntity
import com.thell.focus.database.entity.TimerEntity
import com.thell.focus.database.util.IDatabaseOperation

@Dao
interface TimerDao : IDatabaseOperation<TimerEntity>
{
    @Query("update Timer set  IsFinish = :isFinish where ID = :id ")
    fun update(id:Int,isFinish:Boolean)

    @Query("update Timer set  IsFinish = :isFinish ")
    fun updateAll(isFinish:Boolean)

    @Query("select * from Timer")
    override fun getAll(): LiveData<List<TimerEntity>>

    @Query("select * from Timer")
    override fun getAllList(): List<TimerEntity>

    @Query("select  * from Timer where IsFinish = :state ")
    fun getLastTimer(state:Boolean = false):TimerEntity
}