package kz.jcourier.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.jcourier.common.BaseApiResponse
import kz.jcourier.common.NetworkResult
import kz.jcourier.data.api.LoginApiService
import kz.jcourier.data.model.auth.LoginModel
import kz.jcourier.data.model.auth.TokenModeleData
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepository @Inject constructor(
    private val loginApiService: LoginApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun login(phone: String, password: String): NetworkResult<TokenModeleData> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                loginApiService.login(
                    LoginModel(
                        phone,
                        password
                    )
                )
            }
        }
    }
}