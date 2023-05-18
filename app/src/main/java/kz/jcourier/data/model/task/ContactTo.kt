package kz.jcourier.data.model.task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactTo(
    var id: Int? = null,
    var name: String? = null,
    var phone: String? = null
) : Parcelable
