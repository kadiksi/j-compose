package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class WorkingTime(
    var hour: ScheduleHour? = null,
    var breakTime: ScheduleHour? = null,
    var days: ArrayList<ScheduleDay>? = null,
) : Parcelable

@Parcelize
@Keep
data class ScheduleHour(
    var from: String? = null,
    var to: String? = null,
) : Parcelable

@Parcelize
@Keep
data class ScheduleDay(
    var id: String? = null,
    var day: String? = null,
) : Parcelable