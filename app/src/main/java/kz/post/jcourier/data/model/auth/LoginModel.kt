package kz.post.jcourier.data.model.auth

import androidx.annotation.Keep

@Keep
data class LoginModel(
    val login: String,
    val password: String,
    val user_type: String = "K"
)