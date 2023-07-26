package kz.post.jcourier.data.model.shift

import androidx.annotation.Keep

@Keep
data class CourierModel(
    val id: String? = null,
    val userId: String? = null,
    val cityId: String? = null,
    val info: String? = null,
    val status: Shift? = null,
    val rating: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val phone: String? = null,
)