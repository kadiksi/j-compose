package kz.post.jcourier.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.post.jcourier.common.BaseApiResponse
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.data.api.LoginApiService
import kz.post.jcourier.data.model.auth.ChangePasswordModel
import kz.post.jcourier.data.model.auth.LoginModel
import kz.post.jcourier.data.model.auth.RefreshToken
import kz.post.jcourier.data.model.auth.TokenModelData
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepository @Inject constructor(
    private val loginApiService: LoginApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun login(phone: String, password: String): NetworkResult<TokenModelData> {
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

    suspend fun changePassword(
        login: String,
        currentPassword: String,
        password: String,
        passwordConfirmation: String
    ): NetworkResult<TokenModelData> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                loginApiService.passwordChangeGetTokens(
                    ChangePasswordModel(
                        login = login,
                        password = password,
                        current_password = currentPassword,
                        password_confirmation = passwordConfirmation
                    )
                )
            }
        }
    }

    suspend fun sendToken(token: String): NetworkResult<TokenModelData> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                loginApiService.sendToken(token)
            }
        }
    }

    suspend fun refreshToken(token: String): NetworkResult<TokenModelData> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                loginApiService.refreshToken(RefreshToken(token))
            }
        }
    }
}