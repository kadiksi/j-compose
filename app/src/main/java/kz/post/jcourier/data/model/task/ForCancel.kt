package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.util.Date

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
    val unreadMessages: Int
) : Parcelable

@Parcelize
@Keep
data class CanceledNotification(
    val id: Long,
    val createdDate: Date,
    val userId: Long,
    val taskId: Long,
    val title: String,
    val message: String,
    val isRead: Boolean
) : Parcelable

@Parcelize
@Keep
data class Pageable(
    val offset: Long,
    val unpaged: Boolean,
    val paged: Boolean,
    val pageNumber: Long,
    val pageSize: Long
) : Parcelable