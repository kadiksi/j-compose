package kz.post.jcourier.data.api

import kz.post.jcourier.data.model.shift.CourierModel
import kz.post.jcourier.data.model.shift.ShiftModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShiftApiService {

    @GET("gw/jpost-employee/api/public/v1/courier/current")
    suspend fun getShift(): Response<CourierModel>
    
    @POST("gw/jpost-employee/api/public/v1/courier/status")
    suspend fun setShift(@Body model: ShiftModel): Response<CourierModel>
}
