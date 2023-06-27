package kz.post.jcourier.data.model.task

import androidx.annotation.Keep

@Keep
class TaskStatusId(
    val taskId: Long,
    val event: TaskStatus
)