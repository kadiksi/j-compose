package kz.post.jcourier.data.model.shift

import androidx.annotation.Keep

@Keep
data class ShiftModel(
    val status: Shift,
)

enum class Shift {
    ON_SHIFT,
    FREE
}