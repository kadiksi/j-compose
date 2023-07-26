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
    var addressTo: AddressTo? = AddressTo(),
    var addressFrom: AddressFrom? = AddressFrom(),
    var contactTo: ContactTo? = ContactTo(),
    var contactFrom: ContactFrom? = ContactFrom(),
    var product: ArrayList<Product>? = arrayListOf(),
    var histories: ArrayList<TaskHistory>? = arrayListOf(),
    var actions: ArrayList<TaskStatus> = arrayListOf(),
    var cancellationReasons: ArrayList<CancelReason>? = arrayListOf(),

) : Parcelable
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
