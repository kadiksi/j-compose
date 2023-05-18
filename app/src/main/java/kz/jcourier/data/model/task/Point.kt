package kz.jcourier.data.model.task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Point(
    var longetude: Double? = null,
    var latetude: Double? = null
) : Parcelable