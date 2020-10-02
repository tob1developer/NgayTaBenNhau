package com.kietngo.ngaytabennhau.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Class [Converters] Chuyen doi
 * @link [https://developer.android.com/training/data-storage/room/referencing-data]
 * */

class Converters {

    @TypeConverter
    fun bitmapToString(bitmap: Bitmap) : String? {
        val byteArrayOutputStream : ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
        val byte = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byte,Base64.DEFAULT)
    }

    @TypeConverter
    fun stringToBitmap(string: String): Bitmap? {
        val encodeByte = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.size)
    }

    @TypeConverter
    fun calendarToString(calendar: Calendar): String?{
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        return "$day/$month/$year"
    }
    @TypeConverter
    fun stringToCalendar(date: String): Calendar?{
        val testDate: Date? = SimpleDateFormat("dd/MM/yyyy").parse(date)
        val calendar = Calendar.getInstance()
        calendar.time = testDate
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
        return calendar
    }
}