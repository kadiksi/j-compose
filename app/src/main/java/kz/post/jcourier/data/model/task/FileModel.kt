package kz.post.jcourier.data.model.task

import androidx.annotation.Keep

@Keep
class FileModel(
    val taskId: Long,
    val type: FileType
)

enum class FileType {
    MERCHANT,
    CUSTOMER
}