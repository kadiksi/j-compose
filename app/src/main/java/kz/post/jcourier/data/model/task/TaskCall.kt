package kz.post.jcourier.data.model.task

import androidx.annotation.Keep

@Keep
class TaskCallEvent(
    val taskId: Long,
    val direction: CallDto,
)

@Keep
enum class CallDto {
    SENDER, RECIPIENT
}