package kz.post.jcourier.location.model

data class Time(
    val hour: Int,
    val minute: Int,
) {
    operator fun compareTo(other: Time): Int {
        return when {
            hour > other.hour -> 1
            hour == other.hour && minute > other.minute -> 1
            hour == other.hour && minute == other.minute -> 0
            else -> -1
        }
    }
}