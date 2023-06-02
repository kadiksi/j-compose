package kz.post.jcourier.data.api

import kz.post.jcourier.data.model.auth.LoginModel
import kz.post.jcourier.data.model.auth.TokenModelData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {

    @POST("/gw/user/v1/auth/sign-in")
    suspend fun login(@Body model: LoginModel): Response<TokenModelData>
}
