package com.example.amazonbooks.utils

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.amazonbooks.R
import com.example.amazonbooks.data.local.db.BookEntity
import java.util.concurrent.TimeUnit

fun BookEntity.isGreaterThanQueryThreshold() =
    TimeUnit
        .MILLISECONDS
        .toMinutes(System.currentTimeMillis()) - timeStamp.toLong() > QUERY_THRESHOLD

fun NotificationManager.sendNotification(messageBody: String, appContext: Context) {
    // Building the notification
    val builder = NotificationCompat.Builder(
        appContext,
        appContext.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(appContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(NOTIFICATION_ID, builder.build())
}
