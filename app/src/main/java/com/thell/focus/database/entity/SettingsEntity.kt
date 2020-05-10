package com.thell.focus.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.thell.focus.helper.settings.Settings

@Entity(tableName = "Settings")
data class SettingsEntity
    (
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    var ID: Int = 0,
    @ColumnInfo(name = "SettingsKey")
    var SettingsKey: String = "",
    @ColumnInfo(name = "SettingsDescription")
    var SettingsDescription: String = "",
    @ColumnInfo(name = "State")
    var State: Int = Settings.StateType.NA
)
