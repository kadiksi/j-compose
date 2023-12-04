package kz.post.jcourier.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

val CHANNEL_ID_DEFAULT = "channel_id_courier"
fun createNotificationChannels(context: Context) {
    // Add more channel IDs if needed

    // Add more channel IDs if needed
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the default notification channel
        val defaultChannel = NotificationChannel(
            CHANNEL_ID_DEFAULT,
            "Default Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        // Set additional channel properties if needed
        val notificationManager = context.getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(defaultChannel)
    }
}
