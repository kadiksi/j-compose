package kz.jcourier.location.model

data class TrackingLocation(
    val accuracy: Int,
    val latitude: Double,
    val longitude: Double
)