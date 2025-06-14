package kz.post.jcourier.data.location


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kz.post.jcourier.BuildConfig
import kz.post.jcourier.common.onSuccess
import kz.post.jcourier.data.api.LocationApiService
import kz.post.jcourier.data.repository.LocationRepository
import kz.post.jcourier.data.repository.ShiftRepository
import java.util.Calendar
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt
import kz.post.jcourier.data.repository.TaskRepository
import kz.post.jcourier.data.sharedprefs.SharedPreferencesProvider
import kz.post.jcourier.location.model.Time
import kz.post.jcourier.location.model.TrackingLocation
import kz.post.jcourier.utils.isGpsEnabled

class SendCoordinationUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationRepository: LocationRepository,
) {

    val lastSentLocation: MutableState<TrackingLocation> = mutableStateOf(
        TrackingLocation(
            1,
            71.0,
            51.8
        )
    )
    private val lastSavedLocation = MutableLiveData<TrackingLocation>()
    private val lastLocationSentTime = AtomicLong(NO_TIME)
    private val isSendLocationBusy = AtomicBoolean(false)

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult ?: return
            for (location in locationResult.locations) {
                lastSavedLocation.value = TrackingLocation(
                    accuracy = location.accuracy.roundToInt(),
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            }
        }
    }

    private val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    suspend operator fun invoke() {
        val location = lastSavedLocation.value

        if (location != null) {
            onLocationChanged(
                accuracy = location.accuracy,
                latitude = location.latitude,
                longitude = location.longitude,
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun start() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun stop() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private suspend fun onLocationChanged(accuracy: Int?, latitude: Double?, longitude: Double?) {
        if (accuracy == null || latitude == null || longitude == null) return

        val location = TrackingLocation(
            accuracy = accuracy,
            latitude = latitude,
            longitude = longitude
        )

        val realtime = SystemClock.elapsedRealtime()

//        if (lastSentLocation.value == location ||
//            isSendLocationBusy.get() ||
//            lastLocationSentTime.get() != NO_TIME &&
//            lastLocationSentTime.get() + MINUTE_IN_MILLIS > realtime
//        ) return

        isSendLocationBusy.set(true)
//        if(BuildConfig.DEBUG)
//            return
        locationRepository
            .setLocation(location.latitude, location.longitude)
            .onSuccess {
                Log.e("Location lat ", location.latitude.toString())
                Log.e("Location lon ", location.longitude.toString())
                lastSentLocation.value = location
            }
        isSendLocationBusy.set(false)
        lastLocationSentTime.set(realtime)
    }

    private fun hasGrantedPermission(): Boolean {
        val checkSelfPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val startTime = Time(8, 0)
        private val finishTime = Time(22, 0)

        private const val NO_TIME = -1L
        private const val MINUTE_IN_MILLIS = 60 * 1000
    }
}