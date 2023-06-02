package kz.post.jcourier.data.model.auth

data class TokenModelData(
    var success: Boolean?,
    var data: TokenModel?
)

data class TokenModel(
    var tokens: Tokens?,
)

data class Tokens(
    var auth: Auth,
    var refresh: Refresh?
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