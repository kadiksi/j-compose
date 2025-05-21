package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class ForCancel(
    val totalElements: Long,
    val totalPages: Long,
    val size: Long,
    val content: List<CanceledNotification>,
    val number: Long,
    val pageable: Pageable,
    val numberOfElements: Long,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean,
    val unreadCount: Int
) : Parcelable