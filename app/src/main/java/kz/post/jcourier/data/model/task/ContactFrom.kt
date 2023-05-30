package kz.post.jcourier.data.model.task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactFrom(
    var id: Int? = null,
    var companyName: String? = null,
    var name: String? = null
) : Parcelable
