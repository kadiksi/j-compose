package kz.post.jcourier.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.post.jcourier.common.BaseApiResponse
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.data.api.LoginApiService
import kz.post.jcourier.data.model.auth.TokenModelData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepository @Inject constructor(
    private val loginApiService: LoginApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun sendToken(token: String): NetworkResult<TokenModelData> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                loginApiService.sendToken(token)
            }
        }
    }
}