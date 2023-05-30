package kz.post.jcourier.data.model.auth

data class TokenModeleData(
    var success: Boolean?,
    var data: TokenModel?
)

data class TokenModel(
    var tokens: Tokens?,
    var refresh: Refresh?
)

data class Tokens(
    var auth: Auth
)

data class Auth(
    var token: String? = null,
    var issued: String? = null,
    var expired: String? = null
)

data class Refresh(
    var token: String? = null,
    var issued: String? = null,
    var expired: String? = null
)