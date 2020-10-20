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
import kotlinx.android.synthetic.main.fragment_setting.view.*
import timber.log.Timber

class NotificationLove(
    private val context: Context,private val  activity: Activity
) {

    private val notificationLayout = RemoteViews(context.packageName,R.layout.layout_notification)


    private val builder = NotificationCompat.Builder(context, CHANNEL_NOTIFICATION_ID)
        .setSmallIcon(R.drawable.ic_favorite_24px)
        .setCustomBigContentView(notificationLayout)
        .setOngoing(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)


    fun turnOnNotification(){
        createNotificationChannel()
        notificationLayout.setTextViewText(R.id.nameUser1,"Ngo Tuan Kiet")
        with(NotificationManagerCompat.from(context)){
            notify(ID_NOTIFICATION_CHANNEL,builder.build())
        }
        Timber.d("turn on notification")
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
}
