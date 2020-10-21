package com.kietngo.ngaytabennhau.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kietngo.ngaytabennhau.repository.model.Color
import com.kietngo.ngaytabennhau.repository.model.LoveDate

@Dao
interface ColorDao{
    @Query("SELECT * FROM color")
    fun getAllColor(): LiveData<List<Color>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertColor(color : Color)

    @Query("SELECT * FROM color WHERE id = :colorID")
    fun loadColorWithId(colorID :Int) : Color

    @Update
    suspend fun updateColor(vararg color: Color)
}