package kz.jcourier.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.jcourier.common.BaseApiResponse
import kz.jcourier.common.NetworkResult
import kz.jcourier.data.api.LoginApiService
import kz.jcourier.data.model.auth.LoginModule
import kz.jcourier.data.model.auth.TokenModuleData
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepository @Inject constructor(
    private val loginApiService: LoginApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun login(phone: String, password: String): NetworkResult<TokenModuleData> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                loginApiService.login(
                    LoginModule(
                        phone,
                        password
                    )
                )
            }
        }
    }

    suspend fun getUserRoleList(): NetworkResult<TokenModuleData> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                loginApiService.getUserRoleList()
            }
        }
    }

}