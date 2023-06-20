package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Task(
    var id: Int? = null,
    var userId: Int? = null,
    var status: TaskStatus? = null,
    var serviceId: Int? = null,
    var orderId: Int? = null,
    var routeId: Int? = null,
    var addressTo: AddressTo? = AddressTo(),
    var addressFrom: AddressFrom? = AddressFrom(),
    var contactTo: ContactTo? = ContactTo(),
    var contactFrom: ContactFrom? = ContactFrom(),
    var product: ArrayList<Product>? = arrayListOf(),
    var histories: ArrayList<TaskHistory>? = arrayListOf(),
    var actions: ArrayList<TaskStatus> = arrayListOf(),
) : Parcelable
@Keep
enum class TaskStatus {
    NEW,
    ON_WAY,
    PICK_UP,
    DELIVER,
    CONFIRM,
    COMPLETE,
    CANCEL
}
