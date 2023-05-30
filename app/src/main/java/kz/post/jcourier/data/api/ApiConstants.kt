package kz.post.jcourier.data.api

object ApiConstants {
    // Authorization header, used for most of the API requests.
    const val HEADER_AUTH = "Authorization"

    // Temporary authorization header, used only during registration, and during password reset process.
    const val HEADER_TEMP_AUTH = "Registration"
}