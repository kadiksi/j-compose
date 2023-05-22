package kz.jcourier.data.model.task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TaskHistory(
    val id: Long,
    val createdBy: Long,
    val createdDate: String,
    val lastModifiedBy: Long,
    val lastModifiedDate: String,
    val date: String,
    val action: String,
    val point: Point,
    val reason: String,
    val userId: Long
) : Parcelable