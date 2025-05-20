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
    val unreadCount: Int
) : Parcelable

@Parcelize
@Keep
data class CanceledNotification(
    val id: Long,
    val createdDate: Date,
    val userId: Long,
    val attributes: Attributes,
    val title: FieldData,
    val message: FieldData,
    val isRead: Boolean
) : Parcelable

@Parcelize
@Keep
data class FieldData(
    val EN: String,
    val KK: String,
    val RU: String,
) : Parcelable

@Parcelize
@Keep
data class Attributes(
    val taskId: Long,
    val barcode: String,
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