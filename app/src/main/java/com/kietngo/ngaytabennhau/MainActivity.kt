package com.kietngo.ngaytabennhau


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.kietngo.ngaytabennhau.repository.*
import com.kietngo.ngaytabennhau.repository.model.Color
import com.kietngo.ngaytabennhau.repository.model.LoveDate
import com.kietngo.ngaytabennhau.repository.model.Quote
import com.kietngo.ngaytabennhau.repository.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}