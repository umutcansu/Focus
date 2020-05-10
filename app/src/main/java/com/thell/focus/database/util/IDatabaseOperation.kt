package com.thell.focus.database.util

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface IDatabaseOperation<T>
{
    @Insert
    fun insert(t :T)

    @Delete
    fun delete(t :T)

    @Update
    fun update(t :T)

    fun getAll(): LiveData<List<T>>

    fun getAllList():List<T>
}