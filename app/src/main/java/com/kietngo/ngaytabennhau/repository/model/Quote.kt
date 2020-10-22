package com.kietngo.ngaytabennhau.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
data class Quote(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "content")
    val content: String,
)