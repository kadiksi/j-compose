package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Pageable(
    val offset: Long,
    val unpaged: Boolean,
    val paged: Boolean,
    val pageNumber: Long,
    val pageSize: Long
) : Parcelable