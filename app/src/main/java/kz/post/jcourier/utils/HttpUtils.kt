package kz.post.jcourier.utils

import android.util.Base64
import java.util.*

object HttpUtils {

    fun getBasicAuthHeader(username: String, password: String): String {
        val toEncode = "$username:$password"
        return String.format(
            Locale.getDefault(),
            "Basic %s",
            Base64.encodeToString(toEncode.toByteArray(), Base64.NO_WRAP)
        )
    }

    fun getBearerTokenHeader(token: String?): String {
        return String.format(Locale.getDefault(), "Bearer %s", token)
    }
}