package kz.post.jcourier.common

sealed class NetworkResult<T>(
) {
    class Success<T>(val data: T) : NetworkResult<T>()
    class Error<T>(val message: String, val data: T? = null) : NetworkResult<T>()
    class Loading<T> : NetworkResult<T>()
    class LocationPermissionNotGranted<T> : NetworkResult<T>()

    class GpsNotEnabled<T> : NetworkResult<T>()
}

inline fun <T : Any> NetworkResult<T>.onSuccess(
    onSuccess: (T) -> Unit
): NetworkResult<T> {
    if (this is NetworkResult.Success) onSuccess(this.data)

    return this
}
inline fun <T : Any> NetworkResult<T>.onError(
    onError: (T?) -> Unit
): NetworkResult<T> {
    if (this is NetworkResult.Error) onError(this.data)

    return this
}