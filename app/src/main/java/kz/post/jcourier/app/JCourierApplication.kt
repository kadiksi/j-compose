package kz.post.jcourier.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JCourierApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}