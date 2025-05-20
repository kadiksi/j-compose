package kz.post.jcourier.data.api

import kz.post.jcourier.data.model.auth.ChangePasswordModel
import kz.post.jcourier.data.model.auth.LoginModel
import kz.post.jcourier.data.model.auth.RefreshToken
import kz.post.jcourier.data.model.auth.TokenModelData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginApiService {

    @POST("/gw/user/v1/auth/sign-in")
    suspend fun login(@Body model: LoginModel): Response<TokenModelData>

    @POST("/gw/user/v1/auth/password-change-get-tokens")
    suspend fun passwordChangeGetTokens(@Body model: ChangePasswordModel): Response<TokenModelData>

    @POST("/gw/user/v1/auth/refresh")
    suspend fun refreshToken(@Body refreshToken: RefreshToken): Response<TokenModelData>

    @POST("/gw/jpost-push/api/v1/token/register")
    suspend fun sendToken(@Query("token") token: String): Response<TokenModelData>
}
