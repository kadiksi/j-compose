package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Point(
    var id: Long?,
    var createdBy: Long?,
    var createdDate: String?,
    var lastModifiedBy: Long?,
    var lastModifiedDate: String?,
    var longitude: Double? = null,
    var latitude: Double? = null
) : Parcelable