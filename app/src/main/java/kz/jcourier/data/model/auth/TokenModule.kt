package kz.jcourier.data.model.auth

data class TokenModuleData(
    var success: Boolean?,
    var data: TokenModule?
)

data class TokenModule(
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