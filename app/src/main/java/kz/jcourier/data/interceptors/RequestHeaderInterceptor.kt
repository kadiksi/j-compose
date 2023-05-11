package kz.jcourier.data.interceptors

import android.text.TextUtils
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kz.jcourier.data.sharedprefs.SharedPreferencesProvider
import kz.jcourier.data.api.ApiConstants
import kz.jcourier.utils.HttpUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Adds an access token to the request if it's available.
 */
@Singleton
class RequestHeaderInterceptor @Inject constructor(
    private var sharedPreferencesProvider: SharedPreferencesProvider
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val accessToken = sharedPreferencesProvider.accessToken

        // If the request already has an auth header, we will not override it, since
        // a manually set header should have a higher priority.
        val hasAuthHeader = original.header(ApiConstants.HEADER_AUTH) != null ||
                original.header(ApiConstants.HEADER_TEMP_AUTH) != null
        var builder: Request.Builder? = null
        if (!hasAuthHeader && !TextUtils.isEmpty(accessToken)) {

            // Put the token into the "Authorization" header.
            builder = original.newBuilder()

            // The HEADER_AUTH takes precedence over HEADER_TEMP_AUTH.
            if (accessToken != null) {
                builder.header(
                    ApiConstants.HEADER_AUTH,
                    HttpUtils.getBearerTokenHeader(accessToken)
                )
            }
        }
        return if (builder != null) {
            chain.proceed(builder.build())
        } else {
            chain.proceed(original)
        }
    }
}