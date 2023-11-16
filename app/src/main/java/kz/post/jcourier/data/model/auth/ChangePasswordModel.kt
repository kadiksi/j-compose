package kz.post.jcourier.data.model.auth

import androidx.annotation.Keep

@Keep
data class ChangePasswordModel(
    val login: String,
    val current_password: String,
    val password: String,
    val password_confirmation: String,
    val user_type: String = "K"
)