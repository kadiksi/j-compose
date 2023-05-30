package kz.post.jcourier.data.model.task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressTo(
    var id: Int? = null,
    var cityId: Int? = null,
    var address: String? = null,
    var point: Point? = null
) : Parcelable
