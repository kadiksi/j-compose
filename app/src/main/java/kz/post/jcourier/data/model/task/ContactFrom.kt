package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Keep
data class ContactFrom(
    var id: Long? = null,
    var createdDate: Date? = null,
    var companyName: String? = null,
    var name: String? = null,
    var phone: String? = null,
    var comments: String? = null,
    var dateFrom: Date? = null,
    var dateTo: Date? = null,
    var workingTime: ArrayList<WorkingTime>? = null,
    var workSchedule: ArrayList<WorkSchedule>? = null,
) : Parcelable {

    fun getSchedule(): String {
        if (workSchedule.isNullOrEmpty())
            return ""
        var schedule = ""
        workSchedule?.forEach {
            schedule += "${it.days} \n${it.workTimeInterval} \nПерерыв: ${it.breakTimeInterval}\n\n"
        }
        return schedule.trim()
    }
}
