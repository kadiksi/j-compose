package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class ContactTo(
    var id: Long? = null,
    var name: String? = null,
    var phone: String? = null
) : Parcelable
