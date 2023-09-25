package kz.post.jcourier.data.model.location

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class LocationPoint(
    var longitude: Double,
    var latitude: Double
) : Parcelable