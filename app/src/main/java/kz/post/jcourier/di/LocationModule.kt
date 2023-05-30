package kz.post.jcourier.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kz.post.jcourier.location.FusedLocationProvider
import kz.post.jcourier.location.LocationProvider
import kz.post.jcourier.location.sync.CoroutinesWait
import kz.post.jcourier.location.sync.Wait
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationModule {

    @Singleton
    @Provides
    fun provideWait(): Wait {
        return CoroutinesWait()
    }

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideLocationProvider(
        fusedLocationProviderClient: FusedLocationProviderClient,
        wait: Wait,
    ): LocationProvider {
        return FusedLocationProvider(
            fusedLocationProviderClient = fusedLocationProviderClient,
            wait = wait
        )
    }
}