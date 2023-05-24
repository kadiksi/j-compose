package kz.jcourier.location

import android.annotation.SuppressLint
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kz.jcourier.common.NetworkResult
import kz.jcourier.location.model.TrackingLocation
import kz.jcourier.location.sync.WaitingTimeoutException
import kz.jcourier.utils.hasPermissionAccessFineLocation
import kz.jcourier.utils.isGpsEnabled

@Singleton
class GetLastKnowLocationService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationProvider: LocationProvider,
) {

    @SuppressLint("MissingPermission")
    suspend operator fun invoke(): NetworkResult<TrackingLocation> {
        if (!context.hasPermissionAccessFineLocation())
            return NetworkResult.LocationPermissionNotGranted()
        if (!context.isGpsEnabled())
            return NetworkResult.GpsNotEnabled()

        return try {
            NetworkResult.Success(locationProvider.getLocation())
        } catch (e: WaitingTimeoutException) {
            NetworkResult.LocationPermissionNotGranted()
        } catch (e: NoLocationFoundException) {
            NetworkResult.GpsNotEnabled()
        }
    }

    private companion object {
        private const val NOT_FOUND = """Не удалось получить местоположение.
                                |
                                |Пожалуйста, повторите попытку через 10 секунд."""
        private const val TIME_OUT = """Не удалось получить местоположение.
                                |
                                |Время ожидания вышло. Пожалуйста, проверьте подключение к интернету и повторите попытку позже."""
    }
}