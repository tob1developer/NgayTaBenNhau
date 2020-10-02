package com.kietngo.ngaytabennhau.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kietngo.ngaytabennhau.repository.model.Color
import com.kietngo.ngaytabennhau.repository.model.LoveDate

@Dao
interface ColorDao{
    @Query("SELECT * FROM color")
    fun getAllColor(): List<Color>

    @Insert
    suspend fun insertColor(color : Color)

    @Query("SELECT * FROM color WHERE id = :colorID")
    suspend fun loadColor(colorID :Int) : Color

    @Update
    suspend fun updateColor(vararg color: Color)
}