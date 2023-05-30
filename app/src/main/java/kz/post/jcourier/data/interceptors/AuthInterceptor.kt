package kz.post.jcourier.data.interceptors

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kz.post.jcourier.data.sharedprefs.SharedPreferencesProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPreferencesProvider: SharedPreferencesProvider
): Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            sharedPreferencesProvider.accessToken
        }
        val request = chain.request()
        if (!token.isNullOrEmpty()) {
            val newRequest = request
                .newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            val response = chain.proceed(newRequest)
            return if (response.code == 401) {
                response
                //refreshToken()
            } else {
                response
            }
        } else {
            //refreshToken()
        }
        return chain.proceed(request)
    }
}