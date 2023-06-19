package kz.post.jcourier.data.model.shift

import androidx.annotation.Keep

@Keep
data class CourierModel(
    val id: String,
    val userId: String,
    val cityId: String,
    val info: String,
    val status: Shift,
    val rating: String,
)