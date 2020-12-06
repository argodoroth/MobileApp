package com.example.newsmate

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat

internal class NotificationHelper (base: Context): ContextWrapper(base) {
    private var notManager: NotificationManager? = null
    private val intent = Intent(this, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    private val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0,intent,0)
    //Assigns the notManager to notification service
    private val manager: NotificationManager?
        get() {
            if (notManager == null) {
                notManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return notManager
        }

    //When is made will set up notification channels
    init {
        createChannels()
    }

    fun createChannels() {
        //Makes setup for channel, then passes to manager to create
        val notificationChannel =
            NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.setShowBadge(true)
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        manager!!.createNotificationChannel(notificationChannel)

    }

    //Bind info to the notification
    fun getNotification1(title:String, body:String): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, CHANNEL_ONE_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.n_logo)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }


    //Will build the notification and post to channel
    fun notify(id: Int, notification: NotificationCompat.Builder) {
        manager!!.notify(id, notification.build())
    }

    companion object {
        const val CHANNEL_ONE_ID = "com.example.newsmate.ONE"
        const val CHANNEL_ONE_NAME = "Channel One"
    }
}