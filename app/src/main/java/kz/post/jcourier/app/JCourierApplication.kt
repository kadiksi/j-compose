package kz.post.jcourier.app

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import kz.post.jcourier.firebase.createNotificationChannels

@HiltAndroidApp
class JCourierApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize notification channels
        createNotificationChannels(this)

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
    }
}