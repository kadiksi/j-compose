package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class SmsTimer(
    var time: String = "",
    var canSendSms: Boolean = true,
) : Parcelable