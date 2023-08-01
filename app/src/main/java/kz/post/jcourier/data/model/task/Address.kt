package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Keep
data class Address(
    var id: Long? = null,
    var createdDate: Date? = null,
    var cityId: Long? = null,
    var address: String? = null,

    var street: String? = null,
    var streetAbr: String? = null,
    var building: String? = null,
    var flat: String? = null,
    var entrance: String? = null,
    var floor: String? = null,
    var intercom: String? = null,
    var comment: String? = null,

    var point: Point? = null
) : Parcelable
