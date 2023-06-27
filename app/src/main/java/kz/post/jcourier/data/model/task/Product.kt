package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Product(
    var id: Long? = null,
    var orderId: Long? = null,
    var productId: Long? = null,
    var name: String? = null,
    var quantity: Long? = null,
    var delivered: Boolean? = null
) : Parcelable {
    override fun toString(): String {
        return "$id $orderId $productId $name $quantity $delivered"
    }
}
