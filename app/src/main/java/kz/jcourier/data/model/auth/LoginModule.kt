package kz.jcourier.data.model.auth

data class LoginModule(
    val login: String,
    val password: String,
    val user_type: String = "K"
)