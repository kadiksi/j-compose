package kz.post.jcourier.data.model.task

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class FieldData(
    val EN: String,
    val KK: String,
    val RU: String,
) : Parcelable