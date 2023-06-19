package kz.post.jcourier.data.model.location

import androidx.annotation.Keep

@Keep
data class LocationModel(
    val date: String,
    val point: LocationPoint,
)