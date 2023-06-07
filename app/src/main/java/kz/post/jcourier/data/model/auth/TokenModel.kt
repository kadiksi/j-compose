package kz.post.jcourier.data.model.auth

import androidx.annotation.Keep

@Keep
data class TokenModelData(
    var success: Boolean?,
    var data: TokenModel?
)

@Keep
data class TokenModel(
    var tokens: Tokens?,
)

@Keep
data class Tokens(
    var auth: Auth,
    var refresh: Refresh?
)

@Keep
data class Auth(
    var token: String? = null,
    var issued: String? = null,
    var expired: String? = null
)

@Keep
data class Refresh(
    var token: String? = null,
    var issued: String? = null,
    var expired: String? = null
)