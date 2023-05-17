package kz.jcourier.data.api

import kz.jcourier.data.model.auth.LoginModel
import kz.jcourier.data.model.auth.TokenModeleData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {

    @POST("/gw/user/v1/auth/sign-in")
    suspend fun login(@Body model: LoginModel): Response<TokenModeleData>

    @GET("gw/jpost-courier/api/public/v1/task")
    suspend fun getUserRoleList(): Response<TokenModeleData>
}
