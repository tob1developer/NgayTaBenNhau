package com.kietngo.ngaytabennhau.repository.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "love_date")
data class LoveDate(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "start_date")
    var startDate: Calendar?,
    @ColumnInfo(name = "top_title")
    var topTitle: String?,
    @ColumnInfo(name = "bottom_title")
    var bottomTitle: String?,
    @ColumnInfo(name = "wall_page")
    var wallPage: Bitmap?,
    @ColumnInfo(name = "love_color")
    var loveColor: String?,
    @ColumnInfo(name ="status_notification")
    var statusNotification : Int
    )