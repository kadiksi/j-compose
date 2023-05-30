package kz.post.jcourier.data.exception

class ApiTokenRefreshException(
    message: String?, val responseCode: Int, val refreshToken: String
) : Exception(message)