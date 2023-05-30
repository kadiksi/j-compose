package kz.post.jcourier.data.model.auth

data class LoginModel(
    val login: String,
    val password: String,
    val user_type: String = "K"
)