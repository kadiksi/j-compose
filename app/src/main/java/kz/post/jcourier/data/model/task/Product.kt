package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Product(
    var id: Int? = null,
    var orderId: Int? = null,
    var productId: Int? = null,
    var name: String? = null,
    var quantity: Int? = null,
    var delivered: Boolean? = null
) : Parcelable {
    override fun toString(): String {
        return "$id $orderId $productId $name $quantity $delivered"
    }
}
