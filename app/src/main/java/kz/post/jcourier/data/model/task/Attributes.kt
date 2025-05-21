package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Attributes(
    val taskId: Long,
    val barcode: String,
) : Parcelable