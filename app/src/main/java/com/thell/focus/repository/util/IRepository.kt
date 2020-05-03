package com.thell.focus.repository.util

import androidx.lifecycle.LiveData

interface IRepository<T>
{
    fun insert(t :T)


    fun delete(t :T)


    fun update(t :T)


    fun getAll(): LiveData<List<T>>

}