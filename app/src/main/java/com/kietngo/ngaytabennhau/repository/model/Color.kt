package com.kietngo.ngaytabennhau.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "color")
data class Color (
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "quote_of_color")
    var quoteOfColor: String, // mau ung voi dac trung cua no
    @ColumnInfo(name = "color_hext")
    val ColorToHex: String
)