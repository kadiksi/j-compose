package kz.post.jcourier.location


import android.annotation.SuppressLint
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import kz.post.jcourier.location.model.TrackingLocation
import kz.post.jcourier.location.sync.Wait
import timber.log.Timber

interface LocationProvider {
    suspend fun getLocation(): TrackingLocation
}

class FusedLocationProvider constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val wait: Wait
) : LocationProvider {

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): TrackingLocation {
        val location = fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            Log.e("Location", it.latitude.toString())
        }.addOnFailureListener {
            Timber.e(it)
        }

        wait.waitWhile(TIME_OUT_MILLIS) { !location.isComplete }

        val result = location.result
        if (result != null) {
            return TrackingLocation(
                accuracy = result.accuracy.toInt(),
                latitude = result.latitude,
                longitude = result.longitude
            )
        } else {
            throw NoLocationFoundException("NoLocationFoundException")
        }
    }

    companion object {
//                private const val TIME_OUT_MILLIS = 90_000L
        private const val TIME_OUT_MILLIS = 5000L
    }
}

class NoLocationFoundException(message: String? = null) : Exception(message)