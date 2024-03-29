package kz.post.jcourier.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.post.jcourier.common.BaseApiResponse
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.data.api.LoginApiService
import kz.post.jcourier.data.api.TaskApiService
import kz.post.jcourier.data.model.auth.TokenModelData
import kz.post.jcourier.data.model.task.*
import okhttp3.MultipartBody
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