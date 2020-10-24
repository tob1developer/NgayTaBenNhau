package com.kietngo.ngaytabennhau.ui.notification

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.repository.CHANNEL_NOTIFICATION_ID
import com.kietngo.ngaytabennhau.repository.ID_NOTIFICATION_CHANNEL
import com.kietngo.ngaytabennhau.repository.NAME_CHANNEL_NOTIFICATION
import com.kietngo.ngaytabennhau.repository.model.LoveDate
import com.kietngo.ngaytabennhau.repository.model.User
import kotlinx.android.synthetic.main.fragment_setting.view.*
import timber.log.Timber
import java.util.*

class NotificationLove(
    private val context: Context,private val  activity: Activity
) {

    private val notificationLayout = RemoteViews(context.packageName,R.layout.layout_notification)


    private val builder = NotificationCompat.Builder(context, CHANNEL_NOTIFICATION_ID)
        .setSmallIcon(R.drawable.ic_favorite_24px)
        .setCustomBigContentView(notificationLayout)
        .setOngoing(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)


    fun turnOnNotification(){
        createNotificationChannel()
        updateNotification()
        Timber.d("turn on notification")
    }


    fun updateUser(user: User, idName: Int,idBirthDay: Int ){
        val nameUser = user.nickName
        val dateUser = user.birthDay
        notificationLayout.apply {
            setTextViewText(idName, nameUser)
            setTextViewText(idBirthDay, dateUser)
        }
        updateNotification()
    }

    fun updateDay(loveDate: LoveDate){
        val day = loveDate.startDate?.let { setDay(it) }
        notificationLayout.setTextViewText(R.id.date, day)

        updateNotification()
    }

    // Update
    private fun updateNotification(){
        with(NotificationManagerCompat.from(context)){
            notify(ID_NOTIFICATION_CHANNEL,builder.build())
        }
    }
    fun cancelNotification(){
        val notificationManager: NotificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(ID_NOTIFICATION_CHANNEL)

        Timber.d("cancel notification")
    }

    private fun createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NAME_CHANNEL_NOTIFICATION
            val descriptionText = "Show information Been Together"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_NOTIFICATION_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setDay(calendar: Calendar): String{
        val today: Calendar = Calendar.getInstance()
        val time = today.time.time - calendar.time.time

        val day = time/(1000*60*60*24)
        return "$day day"
    }
}
