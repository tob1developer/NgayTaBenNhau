package com.kietngo.ngaytabennhau.repository.model

import android.graphics.Bitmap
import androidx.room.*
import java.util.*


@Entity(tableName = "user")
data class User (
    @PrimaryKey
    val id : Int,
    @ColumnInfo(name = "avatar")
    var avatar : Bitmap?,
    @ColumnInfo(name = "nick_name")
    var nickName: String,
    @ColumnInfo(name = "birth_day")
    var birthDay: String,
    @ColumnInfo(name = "gender")
    var gender: Int,
    @ColumnInfo(name = "border_color")
    var borderColor: String
)