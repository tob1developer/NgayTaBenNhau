package com.kietngo.ngaytabennhau.repository.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.kietngo.ngaytabennhau.repository.ID_LOVE_DATE
import com.kietngo.ngaytabennhau.repository.dao.LoveDateDao
import com.kietngo.ngaytabennhau.repository.model.LoveDate
import com.kietngo.ngaytabennhau.repository.model.User

class LoveDateRepository(private val loveDateDao: LoveDateDao){

    val loveDate : LiveData<LoveDate> = loveDateDao.loadLoveDate(ID_LOVE_DATE)

    @WorkerThread
    suspend fun updateLoveDate(loveDate: LoveDate){
        loveDateDao.updateLoveDate(loveDate)
    }
}