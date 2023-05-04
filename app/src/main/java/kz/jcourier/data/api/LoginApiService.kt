package kz.jcourier.data.api

import kz.jcourier.data.model.auth.LoginModule
import kz.jcourier.data.model.auth.TokenModuleData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {

    @POST("/gw/user/v1/auth/sign-in")
    suspend fun login(@Body model: LoginModule): Response<TokenModuleData>

    @GET("/gw/user/v1/profile")
    suspend fun getUserRoleList(): Response<TokenModuleData>
}
