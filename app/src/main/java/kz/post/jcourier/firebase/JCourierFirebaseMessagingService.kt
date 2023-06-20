package kz.post.jcourier.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kz.post.jcourier.data.sharedprefs.SharedPreferencesProvider
import javax.inject.Inject

@AndroidEntryPoint
class JCourierFirebaseMessagingService : FirebaseMessagingService() {


//    @Inject
//    internal lateinit var pushNotification: PushNotification

    @Inject
    internal lateinit var sharedPreferencesProvider: SharedPreferencesProvider

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        pushNotification.notify(remoteMessage)
        Log.e("onMessageReceived", remoteMessage.toString())
    }

    override fun onNewToken(token: String) {
        sharedPreferencesProvider.fcmToken = token
        Log.e("onNewToken", token)
    }
}