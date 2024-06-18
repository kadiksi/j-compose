package kz.post.jcourier.data.model.auth

import androidx.annotation.Keep

@Keep
data class TokenModelData(
    var success: Boolean?,
    var data: TokensModel?
)

@Keep
data class TokensModel(
    var tokens: Tokens?,

    var status: Int?,
    var error: String?,
    var message: String?,
    var requestId: String?,
    var hostname: String?
)

@Keep
data class Tokens(
    var auth: TokenModel,
    var refresh: TokenModel?
)

@Keep
data class RefreshToken(
    var token: String? = null,
)

@Keep
data class TokenModel(
    var token: String? = null,
    var issued: String? = null,
    var expired: String? = null
)