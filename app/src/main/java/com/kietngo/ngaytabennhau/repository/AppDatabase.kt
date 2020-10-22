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
import java.util.*
import kotlin.collections.ArrayList

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

        private class AppDatabaseCallback(
            private val scope: CoroutineScope, val context: Context
        ): RoomDatabase.Callback(){

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateColorDatabase(database.colorDao())
                        populateLoveDateDatabase(database.loveDateDao(), context)
                        populateQuoteDatabase(database.quoteDao())
                        populateUserDatabase(database.userDao(), context)
                    }
                }
            }
        }

        fun populateColorDatabase(colorDao: ColorDao){
            val list = ArrayList<String>()
            list.add("#7abfaa")
            list.add("#347c41")
            list.add("#e970ad")
            list.add("#0cc419")
            list.add("#386b75")
            list.add("#5b13a7")
            list.add("#be97b6")
            list.add("#7aea9b")
            list.add("#0a71f1")
            list.add("#c45b63")
            list.add("#78a51d")
            list.add("#ab7992")

            list.forEachIndexed{ tmp, color ->
                val color = Color(tmp, "quote Color",color)
                colorDao.insertColor(color)
            }

            Timber.d("Khoi tao Color")
        }

        fun populateLoveDateDatabase(loveDateDao: LoveDateDao, context: Context){
            val bitmapDefaultWallPage
                    = BitmapFactory.decodeResource(context.resources,R.drawable.whilte_wall_pager)
                val calendar = Calendar.getInstance()
                val loveDate = LoveDate(
                    ID_LOVE_DATE,
                    calendar,
                    TOP_TITLE_DEFAULT,
                    BOTTOM_TITLE_DEFAULT,
                    bitmapDefaultWallPage,
                    COLOR_DEFAULT
                )
                loveDateDao.insertLoveDate(loveDate)

                Timber.d("khoi tao Date love")
        }

        fun populateQuoteDatabase(quoteDao: QuoteDao){
            var quote = Quote(1,"1")
            quoteDao.insertQuote(quote)
            quote = Quote(2,"2")
            quoteDao.insertQuote(quote)
            quote = Quote(3,"3")
            quoteDao.insertQuote(quote)
            quote = Quote(4,"4")
            quoteDao.insertQuote(quote)
            quote = Quote(5,"5")
            quoteDao.insertQuote(quote)
            Timber.d("khoi tao quote")

        }

        private fun addQuote(quoteDao: QuoteDao,id: Int, content: String){
            val quote = Quote(id, content)
            quoteDao.insertQuote(quote)
        }

        fun populateUserDatabase(userDao: UserDao, context: Context){
            val bitmapDefault
                    = BitmapFactory.decodeResource(context.resources,R.drawable.avatar_image_view)

            //User 1
            val defaultUser1 = User(
                ID_USER_1,
                bitmapDefault,
                NICK_NAME_DEFAULT,
                BIRTH_DAY_DEFAULT,
                GENDER_MALE,
                COLOR_DEFAULT
            )
            userDao.insertUser(defaultUser1)

            //User 2
            val defaultUser2 = User(
                ID_USER_2,
                bitmapDefault,
                NICK_NAME_DEFAULT,
                BIRTH_DAY_DEFAULT,
                GENDER_FEMALE,
                COLOR_DEFAULT
            )
            userDao.insertUser(defaultUser2)
        }
    }


}