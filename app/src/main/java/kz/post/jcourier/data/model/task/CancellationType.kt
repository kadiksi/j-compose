package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class CancellationType(
    var cancellationType: Cancellation,
    var description: String,
    var cancellationReasons: MutableList<CancelReason>? = arrayListOf(),
) : Parcelable

@Keep
enum class Cancellation{
    RESCHEDULE_DELIVERY,
    COMPLETELY_CANCEL
}