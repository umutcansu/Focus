package com.thell.focus.database.entity

import androidx.room.*
import java.io.Serializable


@Entity(tableName = "Notification")
data class NotificationEntity
    (
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    var ID: Int = 0,
    @ColumnInfo(name = "PackageName")
    var PackageName: String = "",
    @ColumnInfo(name = "ApplicationName")
    var ApplicationName: String = "",
    @ColumnInfo(name = "IconId")
    var IconId: String="",
    @ColumnInfo(name = "Ticket")
    var Ticket: String="",
    @ColumnInfo(name = "Category")
    var Category: String="",
    @ColumnInfo(name = "PostTime")
    var PostTime: Long=0,
    @ColumnInfo(name = "NotificationID")
    var NotificationID: Int=0,
    @ColumnInfo(name = "MuteState")
    var MuteState: Boolean=false
):Serializable