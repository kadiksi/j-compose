package kz.post.jcourier.data.api

import kz.post.jcourier.data.model.auth.LoginModel
import kz.post.jcourier.data.model.auth.TokenModelData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginApiService {

    @POST("/gw/user/v1/auth/sign-in")
    suspend fun login(@Body model: LoginModel): Response<TokenModelData>

    @POST("/gw/jpost-employee/api/public/v1/employee/token")
    suspend fun sendToken(@Query("token") token: String): Response<TokenModelData>
}
