package kz.jcourier.data.model.task

data class Product(
    var id: Int? = null,
    var orderId: Int? = null,
    var productId: Int? = null,
    var name: String? = null,
    var quantity: Int? = null,
    var delivered: Boolean? = null
)
