package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.util.Date

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