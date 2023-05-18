package kz.jcourier.data.model.task

data class AddressFrom(
    var id: Int? = null,
    var cityId: Int? = null,
    var address: String? = null,
    var point: Point? = Point()
)
