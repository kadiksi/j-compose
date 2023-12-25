package kz.post.jcourier.firebase

data class IsNotification(
    var isNotification: Boolean,
    var title: String? = null,
    var body: String? = null,
    var id: Long? = null
)