package kz.post.jcourier.data.model.task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
    var product: ArrayList<Product> = arrayListOf(),
    var histories: ArrayList<TaskHistory> = arrayListOf()
) : Parcelable

enum class TaskStatus {
    NEW,
    ASSIGNED,
    ON_WAY,
    PICK_UP,
    DELIVER,
    CONFIRM,
    FINISHED,
    CANCEL
}
