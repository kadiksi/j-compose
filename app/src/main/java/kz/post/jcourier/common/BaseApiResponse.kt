package kz.post.jcourier.common

import org.json.JSONObject
import retrofit2.Response
import java.util.Date

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            if (response.code() == 409) {
                val js = JSONObject(response.errorBody()!!.string())
                if(js.has("nextSendTime")){
                    return smsError(js.getString("description"), js.getString("nextSendTime"), response.body())
                }
                return error(js.getString("description"), response.body())
            }
            if (response.code() == 403) {
                return NetworkResult.Error(JSONObject(response.errorBody()!!.string()).getString("description"), response.body())
            }
            return error("${response.code()} ${response.message()}", response.body())
        } catch (e: Exception) {
            return error(e.message ?: e.toString(), null)
        }
    }

    private fun <T> error(errorMessage: String, body: T?): NetworkResult<T> =
        NetworkResult.Error(errorMessage, body)

    private fun <T> smsError(errorMessage: String, date : String, body: T?): NetworkResult<T> =
        NetworkResult.SmsError(errorMessage, date, body)
}