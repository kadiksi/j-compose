package kz.post.jcourier.location.model

import androidx.annotation.Keep

@Keep
data class TrackingLocation(
    val accuracy: Int,
    val latitude: Double,
    val longitude: Double
)