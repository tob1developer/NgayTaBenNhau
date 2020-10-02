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
        val database =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, NAME_DATABASE)
                .build()



        lifecycleScope.launch(Dispatchers.IO){
            val bitmapDefault = BitmapFactory.decodeResource(resources,R.drawable.avatar_image_view)

            //User 1
            val user1 = database.userDao().loadUserWithId(ID_USER_1)
            if(user1 == null) {
                val defaultUser = User(
                    ID_USER_1,
                    bitmapDefault,
                    "default nick name",
                    "28/12/1999",
                    GENDER_MALE,
                    "#FFFFFF"
                )

                database.userDao().insertUser(defaultUser)
            }

            //User 2
            val user2 = database.userDao().loadUserWithId(ID_USER_2)
            if (user2 == null){
                val defaultUser = User(
                    ID_USER_2,
                    bitmapDefault,
                    "default nick name",
                    "28/12/1999",
                    GENDER_FEMALE,
                    "#FFFFFF"
                )

                database.userDao().insertUser(defaultUser)
                Timber.d("Khoi tao user 1 and user 2")
            }




            if(database.colorDao().getAllColor().isEmpty()){
                for (i in 1.. 30){
                    val color = Color(i,"mau","#80c6b1")
                    database.colorDao().insertColor(color)
                }
                Timber.d("khoi tao list color")
            }


            if (database.loveDateDao().loadLoveDate(ID_LOVE_DATE) == null){
                val bitmapDefaultWallPage = BitmapFactory.decodeResource(resources,R.drawable.whilte_wall_pager)
                val calendar = Calendar.getInstance()
                val loveDate = LoveDate(ID_LOVE_DATE,
                    calendar,
                    " top title default",
                    "bottom title",
                    bitmapDefaultWallPage,
                    "#FFFFFF"
                )
                database.loveDateDao().insertLoveDate(loveDate)
                Timber.d("khoi tao Date love")
            }


            if(database.quoteDao().getAllQuote().isEmpty()){

                addQuote(database,1," Mong chúng ta có thể yêu thương sao cho không bao giờ phải hối tiếc vì tình yêu của chúng ta.")
                addQuote(database,2,"Sự so sánh là kẻ nguy hiểm nhất mà tình yêu có thể quen.")
                addQuote(database,3," Trên đời này chỉ có một thứ không thể miễn cưỡng, đó là tình yêu. Chỉ là nói như vậy, nhưng trên đời này có mấy ai có thể chân chính làm được? – Rất nhiều người biết rõ là không thể nhưng vẫn cưỡng cầu.")
                addQuote(database,4,"Nếu đúng là vết thương vừa lành đã quên cảm giác đau. Nhưng chỉ vì sợ đau, mà không cho hạnh phúc của mình một cơ hội, một con đường thì liệu có đáng không?")
                addQuote(database,5,"Tình yêu luôn cần sự kiên trì của cả hai người. … Yêu một người không phải chỉ có sự nhiệt huyết và lòng can đảm mà còn cần sự khoan dung, tha thứ.")

                Timber.d("khoi tao quote")
            }
        }
    }
    private suspend fun addQuote(database: AppDatabase,id: Int, content: String){
        val quote = Quote(id, content)
        database.quoteDao().insertQuote(quote)
    }
}