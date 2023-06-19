package kz.post.jcourier.utils

import java.text.SimpleDateFormat
import java.util.*

private val yyyyMMdd_T_HHmmssZ = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)

fun toFormatLocalSimpleDateTime(date: Date): String = yyyyMMdd_T_HHmmssZ.format(date)