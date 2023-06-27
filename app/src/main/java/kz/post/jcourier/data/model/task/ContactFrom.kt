package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class ContactFrom(
    var id: Long? = null,
    var companyName: String? = null,
    var name: String? = null
) : Parcelable
