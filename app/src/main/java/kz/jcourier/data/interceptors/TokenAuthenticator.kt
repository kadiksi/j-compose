package kz.jcourier.data.interceptors

import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton
import kz.jcourier.data.exception.ApiTokenRefreshException
import kz.jcourier.data.api.ApiConstants
import kz.jcourier.data.model.auth.TokenModule
import kz.jcourier.data.sharedprefs.SharedPreferencesProvider
import kz.jcourier.di.BasicOkHttpClient
import kz.jcourier.utils.HttpUtils
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber

@Singleton
class TokenAuthenticator @Inject constructor(
    private val gson: Gson,
    @BasicOkHttpClient private val okHttpClient: OkHttpClient,
    private val sharedPreferencesProvider: SharedPreferencesProvider
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // This is a synchronous call
        val updatedToken = getUpdatedToken()
        return if (updatedToken.isNullOrBlank()) {
            null
        } else {
            response.request.newBuilder()
                .header(ApiConstants.HEADER_AUTH, updatedToken)
                .build()
        }
    }

    private fun getUpdatedToken(): String? {
        synchronized(this) {
            val refreshToken = sharedPreferencesProvider.refreshToken ?: ""
            Timber.d("REFRESHING TOKEN with %s", refreshToken)
            val request = makeRefreshTokenRequest(refreshToken)
            Timber.d("Made request: %s", request.toString())

            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body
                if (body != null) {
                    val user = gson.fromJson(body.string(), TokenModule::class.java)
                    response.close()
                    Timber.d("RefreshToken RESPONSE: %s", user.toString())
                    if (user.tokens?.auth?.token != null && user.refresh?.token != null) {
                        sharedPreferencesProvider.setUserData(user)
                        return HttpUtils.getBearerTokenHeader(user.tokens?.auth?.token)
                    } else {
                        throw ApiTokenRefreshException(
                            "access token or refresh token is null",
                            response.code,
                            refreshToken
                        )
                    }
                }
                throw ApiTokenRefreshException("response body empty", response.code, refreshToken)
            } else {
                return null
            }
        }
    }
}