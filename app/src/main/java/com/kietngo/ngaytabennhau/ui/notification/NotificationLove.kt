package com.kietngo.ngaytabennhau.ui.notification

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.repository.CHANNEL_NOTIFICATION_ID
import kotlinx.android.synthetic.main.fragment_setting.view.*
import timber.log.Timber

class NotificationLove(
    private val context: Context,private val  activity: Activity
) {

    private val notificationLayout = RemoteViews(context.packageName,R.layout.layout_notification)

    private val builder = NotificationCompat.Builder(context, CHANNEL_NOTIFICATION_ID)
        .setSmallIcon(R.drawable.ic_arrow_back_24px)
        //.setStyle(NotificationCompat.DecoratedCustomViewStyle())
        .setCustomBigContentView(notificationLayout)
        .setOngoing(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)


    fun turnOnNotification(){
        createNotificationChannel()

        with(NotificationManagerCompat.from(context)){
            notify(2,builder.build())
        }
        Timber.d("turn on notification")
    }

    fun cancelNotification(){
        val notificationManager: NotificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(2)

        Timber.d("cancel notification")
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name channel"
            val descriptionText = "description text"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_NOTIFICATION_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
