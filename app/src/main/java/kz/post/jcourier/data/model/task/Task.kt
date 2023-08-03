package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Task(
    var id: Long? = null,
    var userId: Long? = null,
    var status: TaskStatus? = null,
    var serviceId: Long? = null,
    var orderId: Long? = null,
    var routeId: Long? = null,
    var finalRoute: Boolean? = null,
    var addressTo: Address? = Address(),
    var addressFrom: Address? = Address(),
    var contactTo: ContactFrom? = ContactFrom(),
    var contactFrom: ContactFrom? = ContactFrom(),
    var product: ArrayList<Product>? = arrayListOf(),
    var histories: ArrayList<TaskHistory>? = arrayListOf(),
    var actions: ArrayList<TaskStatus> = arrayListOf(),
    var cancellationReasons: ArrayList<CancelReason>? = arrayListOf(),

    ) : Parcelable {
    fun getAddress(address: Address?): String {
        var text = ""
        address?.address?.let {
            text += it
        }
        address?.flat?.let {
            if (it.isNotEmpty()) {
                text += ", кв.$it"
            }
        }
        address?.entrance?.let {
            if (it.isNotEmpty()) {
                text += ", подъезд: $it"
            }
        }
        address?.floor?.let {
            if (it.isNotEmpty()) {
                text += ", этаж: $it"
            }
        }
        return text
    }
}

@Keep
enum class TaskStatus {
    ACTIVE,
    NEW,
    ON_WAY,
    PICK_UP,
    DELIVER,
    CONFIRM,
    COMPLETE,
    CANCEL
}
