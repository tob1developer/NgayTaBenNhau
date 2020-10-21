package com.kietngo.ngaytabennhau.repository.repository

import androidx.lifecycle.LiveData
import com.kietngo.ngaytabennhau.repository.dao.ColorDao
import com.kietngo.ngaytabennhau.repository.model.Color

class ColorRepository(private val colorDao: ColorDao) {

    val listColor : LiveData<List<Color>> = colorDao.getAllColor()
}