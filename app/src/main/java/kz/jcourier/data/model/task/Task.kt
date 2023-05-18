package kz.jcourier.data.model.task

data class Task(
    var id: Int? = null,
    var userId: Int? = null,
    var status: String? = null,
    var serviceId: Int? = null,
    var orderId: Int? = null,
    var routeId: Int? = null,
    var addressTo: AddressTo? = AddressTo(),
    var addressFrom: AddressFrom? = AddressFrom(),
    var contactTo: ContactTo? = ContactTo(),
    var contactFrom: ContactFrom? = ContactFrom(),
    var product: ArrayList<Product> = arrayListOf(),
    var histories: ArrayList<String> = arrayListOf()
)
