package com.kietngo.ngaytabennhau.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kietngo.ngaytabennhau.repository.model.LoveDate

@Dao
interface LoveDateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLoveDate(loveDate: LoveDate)

    @Query("SELECT * FROM love_date WHERE id = :loveDateID")
    fun loadLoveDate(loveDateID :Int) : LiveData<LoveDate>

    @Update
    suspend fun updateLoveDate(loveDate: LoveDate)
}