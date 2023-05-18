package kz.jcourier.data.model.task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressFrom(
    var id: Int? = null,
    var cityId: Int? = null,
    var address: String? = null,
    var point: Point? = Point()
) : Parcelable
