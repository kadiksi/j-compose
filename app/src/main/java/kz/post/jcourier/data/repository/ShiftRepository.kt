package kz.post.jcourier.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.post.jcourier.common.BaseApiResponse
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.data.api.ShiftApiService
import kz.post.jcourier.data.model.auth.LoginModel
import kz.post.jcourier.data.model.auth.TokenModelData
import kz.post.jcourier.data.model.shift.CourierModel
import kz.post.jcourier.data.model.shift.Shift
import kz.post.jcourier.data.model.shift.ShiftModel
import javax.inject.Inject

@ActivityRetainedScoped
class ShiftRepository @Inject constructor(
    private val shiftApiService: ShiftApiService, private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun setStatus(shift: Shift): NetworkResult<CourierModel> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                shiftApiService.setShift(
                    ShiftModel(
                        shift
                    )
                )
            }
        }
    }

    suspend fun getStatus(): NetworkResult<CourierModel> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                shiftApiService.getShift()
            }
        }
    }
}