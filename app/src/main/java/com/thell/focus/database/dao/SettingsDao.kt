package com.thell.focus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.thell.focus.database.entity.SettingsEntity
import com.thell.focus.database.util.IDatabaseOperation

@Dao
interface SettingsDao : IDatabaseOperation<SettingsEntity>
{


    @Query("update Settings set  State = :state where SettingsKey = :key ")
    fun update(key:String,state:Int)

    @Query("select * from Settings")
    override fun getAll(): LiveData<List<SettingsEntity>>

    @Query("select * from Settings")
    override fun getAllList(): List<SettingsEntity>

    @Query("select * from Settings where SettingsKey = :key")
    fun getBySettingsKey(key:String):LiveData<SettingsEntity?>
}