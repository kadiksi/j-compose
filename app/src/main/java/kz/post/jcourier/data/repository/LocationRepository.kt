package kz.post.jcourier.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.post.jcourier.common.BaseApiResponse
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.data.api.LocationApiService
import kz.post.jcourier.data.model.auth.TokenModelData
import kz.post.jcourier.data.model.location.LocationModel
import kz.post.jcourier.data.model.location.LocationPoint
import kz.post.jcourier.utils.toFormatLocalSimpleDateTime
import java.util.*
import javax.inject.Inject

@ActivityRetainedScoped
class LocationRepository @Inject constructor(
    private val locationApiService: LocationApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun setLocation(longitude: Double, latitude: Double): NetworkResult<TokenModelData> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                locationApiService.setLocation(
                    LocationModel(
                        toFormatLocalSimpleDateTime(Date()), LocationPoint(latitude, longitude)
                    )
                )
            }
        }
    }
}