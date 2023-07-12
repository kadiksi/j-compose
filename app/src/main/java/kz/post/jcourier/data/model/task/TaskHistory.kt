package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Keep
class TaskHistory(
    val id: Long?,
    val createdBy: Long?,
    val createdDate: Date,
    val lastModifiedBy: Long?,
    val lastModifiedDate: Date,
    val date: String?,
    val action: String?,
    val point: Point?,
    val reason: String?,
    val userId: Long?
) : Parcelable