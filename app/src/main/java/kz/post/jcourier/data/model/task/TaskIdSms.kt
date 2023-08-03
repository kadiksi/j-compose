package kz.post.jcourier.data.model.task

import androidx.annotation.Keep

@Keep
class TaskIdSms(
    val taskId: Long,
    val smsCode: String?
)