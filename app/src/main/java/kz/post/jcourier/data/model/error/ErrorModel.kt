package kz.post.jcourier.data.model.error

import androidx.annotation.Keep

@Keep
data class ErrorModel(
    var isError: Boolean = false,
    var text: String = ""
)