package com.thell.focus.repository.util

import com.thell.focus.FocusApplication
import com.thell.focus.database.AppDatabase

abstract class BaseRepository<T> :
    IRepository<T>
{
    val database: AppDatabase
        get() = FocusApplication.getDatabase()
}