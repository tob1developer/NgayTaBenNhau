package com.kietngo.ngaytabennhau.repository

import android.content.Context
import android.graphics.BitmapFactory
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.repository.dao.ColorDao
import com.kietngo.ngaytabennhau.repository.dao.LoveDateDao
import com.kietngo.ngaytabennhau.repository.dao.QuoteDao
import com.kietngo.ngaytabennhau.repository.dao.UserDao
import com.kietngo.ngaytabennhau.repository.model.Color
import com.kietngo.ngaytabennhau.repository.model.LoveDate
import com.kietngo.ngaytabennhau.repository.model.Quote
import com.kietngo.ngaytabennhau.repository.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@Database(entities =
[User::class, Quote::class, LoveDate::class, Color::class],
    version = VERSION_DATABASE)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun quoteDao(): QuoteDao
    abstract fun loveDateDao(): LoveDateDao
    abstract fun colorDao(): ColorDao

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : AppDatabase{
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, NAME_DATABASE
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope, context))
                    .build()
                INSTANCE = instance

                instance
            }
        }

        private class AppDatabaseCallback(private val scope: CoroutineScope, val context: Context): RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateColorDatabase(database.colorDao())
                        populateLoveDateDatabase(database.loveDateDao())
                        populateQuoteDatabase(database.quoteDao())
                        populateUserDatabase(database.userDao(), context)
                    }
                }
            }
        }
        fun populateColorDatabase(colorDao: ColorDao){
            //TODO: Create color
        }

        fun populateLoveDateDatabase(loveDateDao: LoveDateDao){
            // TODO: Create LoveData
        }

        fun populateQuoteDatabase(quote: QuoteDao){
            // TODO: create Quote
        }

        fun populateUserDatabase(userDao: UserDao, context: Context){
            val bitmapDefault = BitmapFactory.decodeResource(context.resources,R.drawable.avatar_image_view)

            //User 1
            val defaultUser1 = User(
                ID_USER_1,
                bitmapDefault,
                "default nick name",
                "28/12/1999",
                GENDER_MALE,
                "#FFFFFF"
            )
            userDao.insertUser(defaultUser1)
            //User 2
            val defaultUser2 = User(
                ID_USER_2,
                bitmapDefault,
                "default nick name",
                "28/12/1999",
                GENDER_FEMALE,
                "#FFFFFF"
            )

            userDao.insertUser(defaultUser2)


        }
    }


}