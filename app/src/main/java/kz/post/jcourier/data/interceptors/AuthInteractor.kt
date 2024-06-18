package kz.post.jcourier.data.interceptors

import kz.post.jcourier.BuildConfig
import kz.post.jcourier.data.api.ApiConstants.HEADER_AUTH
import kz.post.jcourier.data.model.auth.RefreshTokenModel
import kz.post.jcourier.utils.HttpUtils
import okhttp3.FormBody
import okhttp3.Request

/**
 * Create an OkHTTP request for refreshing the access token.
 *
 * @param refreshToken - the refresh token.
 * @return The request.
 */
fun makeRefreshTokenRequest(accessToken: String, refreshToken: String): Request {
    val authHeader = HttpUtils.getBearerTokenHeader(accessToken)
    return doMakeRefreshTokenRequest(authHeader, RefreshTokenModel(refreshToken))
}

private fun doMakeRefreshTokenRequest(authHeader: String, refreshToken: RefreshTokenModel): Request {
    val body = FormBody.Builder()
        .add("token", refreshToken.token)
        .build()

    return Request.Builder()
        .header(HEADER_AUTH, authHeader)
        .header("Accept", "application/json")
        .header("Cache-Control", "no-cache")
        .post(body)
        .url(BuildConfig.HOST + "gw/user/v1/auth/refresh")
        .build()
}