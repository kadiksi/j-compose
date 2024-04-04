package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class CancelReason(
    var cancellationReason: String,
    var description: String,
) : Parcelable
