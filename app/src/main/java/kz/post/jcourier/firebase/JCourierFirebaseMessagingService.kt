package kz.post.jcourier.firebase

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kz.post.jcourier.EntryPointActivity
import kz.post.jcourier.R
import kz.post.jcourier.data.sharedprefs.SharedPreferencesProvider
import kz.post.jcourier.viewmodel.HomeViewModel
import kz.post.jcourier.viewmodel.NotificationViewModel
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class JCourierFirebaseMessagingService : FirebaseMessagingService() {


//    @Inject
//    internal lateinit var pushNotification: PushNotification

    @Inject
    internal lateinit var sharedPreferencesProvider: SharedPreferencesProvider

    @Inject
    lateinit var notificationViewModel: NotificationViewModel

    @Inject
    lateinit var isNotification: MutableState<IsNotification>

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.e(remoteMessage.toString())
        val not = remoteMessage.notification
        val data = remoteMessage.data
        val rawData = remoteMessage.rawData
        val id = data["taskId"]
        val intent = Intent(this, EntryPointActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("taskId", id)
        isNotification.value = IsNotification(
            true,
            remoteMessage.notification!!.title,
            remoteMessage.notification!!.body,
            id?.toLong()
        )

        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE)

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, CHANNEL_ID_DEFAULT)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(remoteMessage.notification!!.title)
                .setContentText(remoteMessage.notification!!.body)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setStyle(NotificationCompat.BigTextStyle().bigText(remoteMessage.notification!!.body))
                .setContentIntent(pendingIntent) // Set the intent to be triggered on notification click
                .setAutoCancel(true)

        // Set additional notification properties if needed


        // Set additional notification properties if needed
        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())

    }

    override fun onNewToken(token: String) {
        sharedPreferencesProvider.fcmToken = token
        notificationViewModel.sendToken(token)
        Log.e("onNewToken", token)
    }
}