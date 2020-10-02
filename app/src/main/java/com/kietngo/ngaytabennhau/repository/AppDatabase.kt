package com.kietngo.ngaytabennhau.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kietngo.ngaytabennhau.repository.dao.ColorDao
import com.kietngo.ngaytabennhau.repository.dao.LoveDateDao
import com.kietngo.ngaytabennhau.repository.dao.QuoteDao
import com.kietngo.ngaytabennhau.repository.dao.UserDao
import com.kietngo.ngaytabennhau.repository.model.Color
import com.kietngo.ngaytabennhau.repository.model.LoveDate
import com.kietngo.ngaytabennhau.repository.model.Quote
import com.kietngo.ngaytabennhau.repository.model.User

@Database(entities =
[User::class, Quote::class, LoveDate::class, Color::class],
    version = VERSION_DATABASE)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun quoteDao(): QuoteDao
    abstract fun loveDateDao(): LoveDateDao
    abstract fun colorDao(): ColorDao
}