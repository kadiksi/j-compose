package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class WorkSchedule(
    var workTimeInterval: String? = null,
    var days: String? = null,
    var breakTimeInterval: String? = null,
) : Parcelable