package kz.post.jcourier.common

import retrofit2.Response

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
            return error("${response.code()} ${response.message()}", response.body())
        } catch (e: Exception) {
            return error(e.message ?: e.toString(), null)
        }
    }

    private fun <T> error(errorMessage: String, body: T?): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage", body)
}