package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class AddressFrom(
    var id: Long? = null,
    var cityId: Long? = null,
    var address: String? = null,
    var point: Point? = null
) : Parcelable
