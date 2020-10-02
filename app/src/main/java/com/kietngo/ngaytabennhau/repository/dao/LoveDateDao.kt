package com.kietngo.ngaytabennhau.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kietngo.ngaytabennhau.repository.model.LoveDate

@Dao
interface LoveDateDao {
    @Insert
    suspend fun insertLoveDate(loveDate: LoveDate)

    @Query("SELECT * FROM love_date WHERE id = :loveDateID")
    suspend fun loadLoveDate(loveDateID :Int) : LoveDate

    @Update
    suspend fun updateLoveDate(loveDate: LoveDate)
}