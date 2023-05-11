package kz.jcourier.data.exception

class ApiTokenRefreshException(
    message: String?, val responseCode: Int, val refreshToken: String
) : Exception(message)