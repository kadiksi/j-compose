package kz.post.jcourier.data.api

import kz.post.jcourier.data.model.auth.TokenModelData
import kz.post.jcourier.data.model.location.LocationModel
import kz.post.jcourier.data.model.shift.ShiftModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LocationApiService {

    @POST("gw/jpost-employee/api/public/v1/courier/location")
    suspend fun setLocation(@Body model: LocationModel): Response<TokenModelData>
}
