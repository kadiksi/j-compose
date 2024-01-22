package kz.post.jcourier.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import kz.post.jcourier.R

const val CHANNEL_ID_DEFAULT = "channel_id_courier"
fun Context.createNotificationChannels(
    channelName: String,
    channelDescription: String
) {if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    val notificationManager = NotificationManagerCompat.from(this)

    val newChannelId = getString(R.string.notification_channel_courier)
    val oldChannel = notificationManager.getNotificationChannel(newChannelId)
    if (oldChannel != null) return

    val sound: Uri =
        Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/" + R.raw.cuckoo_o_clock)
    val audioAttributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .setUsage(AudioAttributes.USAGE_ALARM)
        .build()

    val newChannel = NotificationChannel(
        newChannelId,
        channelName,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        setShowBadge(false)
        setSound(sound, audioAttributes)
        enableLights(true)
        description = channelDescription
        vibrationPattern = longArrayOf(0, 700)
        enableVibration(true)
    }

    notificationManager.createNotificationChannel(newChannel)
}
}
