package kz.post.jcourier.utils

import java.text.SimpleDateFormat
import java.util.*

private val yyyyMMdd_T_HHmmssZ = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)

private val ddMMyyyy= SimpleDateFormat("dd-MM-yyyy", Locale.US)

private val ddMMyyyyHHmm= SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US)

private val HHmm= SimpleDateFormat("HH:mm", Locale.US)

private val minSec = SimpleDateFormat("mm:ss", Locale.US)

fun toFormatLocalSimpleDateTime(date: Date): String = yyyyMMdd_T_HHmmssZ.format(date)

fun parseDateTime(dateTime: String): Date = yyyyMMdd_T_HHmmssZ.parse(dateTime) ?: Date()
fun toDateFormat(date: Date): String = ddMMyyyy.format(date)

fun toDateTimeFormat(date: Date): String = ddMMyyyyHHmm.format(date)

fun toTimeFormat(date: Date): String = HHmm.format(date)

fun toMinSec(date: Date): String = minSec.format(date)