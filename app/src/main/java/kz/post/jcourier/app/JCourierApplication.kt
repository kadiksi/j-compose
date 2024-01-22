package kz.post.jcourier.app

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import kz.post.jcourier.R
import kz.post.jcourier.firebase.createNotificationChannels

@HiltAndroidApp
class JCourierApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize notification channels
        createNotificationChannels(
            getString(R.string.app_name_jcourier),
            getString(R.string.app_name_jcourier)
        )

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
    }
}