package kz.post.jcourier.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.post.jcourier.utils.addCharAtIndex
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.common.onError
import kz.post.jcourier.common.onSuccess
import kz.post.jcourier.data.model.auth.TokensModel
import kz.post.jcourier.data.repository.LoginRepository
import kz.post.jcourier.data.sharedprefs.SharedPreferencesProvider
import kz.post.jcourier.firebase.IsNotification
import javax.inject.Inject

data class LoginState(
    val user: MutableState<TokensModel?> = mutableStateOf(null),
    var isError: MutableState<Boolean> = mutableStateOf(false),
    var isRefreshToken: MutableState<Boolean> = mutableStateOf(false),
    var isPassIncorrect: MutableState<Boolean> = mutableStateOf(false),
    var firebaseToken: MutableState<String?> = mutableStateOf(null),
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    val isLogin: MutableState<IsLogin>,
    val isNotification: MutableState<IsNotification>,
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(LoginState())

    var login: String = ""
    var password: String =""

    init {
        viewModelScope.launch {
            isLogin.value = IsLogin(!sharedPreferencesProvider.accessToken.isNullOrEmpty())
            uiState.isRefreshToken.value = !sharedPreferencesProvider.refreshToken.isNullOrEmpty()
        }
    }

    fun onFingerprintRecognized(){
        refreshToken()
    }

    fun login(phone: String, password: String) = viewModelScope.launch {
        if (phone.length != 10)
            return@launch
        when (val result = loginRepository.login(getValidPhone(phone), password)) {
            is NetworkResult.Success -> {
                result.data.data?.let {
                    if(result.data.data?.error.equals("PASSWORD_EXPIRED")){
                        login = getValidPhone(phone)
                        this@LoginViewModel.password = password
                        uiState.isPassIncorrect.value = true
                        sendToken()
                    } else {
                        sharedPreferencesProvider.saveCredentials(phone, password)
                        saveUserData(it)
                    }
                }
            }
            is NetworkResult.Error -> {
                uiState.isError.value = true
            }
            else -> {
                uiState.isError.value = true
            }
        }
    }

    private fun saveUserData(tokensModel: TokensModel) {
        uiState.isPassIncorrect.value = false
        sharedPreferencesProvider.setUserData(tokensModel)
        uiState.user.value = tokensModel
        uiState.isError.value = false
        isLogin.value = IsLogin(true)
    }

    fun changePassword(
        newPassword: String,
        passwordConfirmation: String
    ) = viewModelScope.launch {
        if (newPassword.length <= 2)
            return@launch
        when (val result = loginRepository.changePassword(
            login, password,  newPassword, passwordConfirmation
        )) {
            is NetworkResult.Success -> {
                result.data.data?.let {
                    saveUserData(it)
                }
            }

            is NetworkResult.Error -> {
                uiState.isError.value = true
            }

            else -> {
                uiState.isError.value = true
            }
        }
    }

    private fun sendToken() = viewModelScope.launch {
        when (val result = uiState.firebaseToken.value?.let { loginRepository.sendToken(it) }) {
            is NetworkResult.Success -> {
                result.data.data?.let {
                }
            }
            is NetworkResult.Error -> {
            }
            else -> {
            }
        }
    }

    private fun refreshToken() = viewModelScope.launch {
        loginRepository.refreshToken(sharedPreferencesProvider.refreshToken!!).onSuccess {
            it.let {
                sharedPreferencesProvider.setUserData(it.data!!)
                sharedPreferencesProvider.login?.let {
                    sharedPreferencesProvider.password?.let { it1 ->
                        login(
                            it,
                            it1
                        )
                    }
                }
            }
        }.onError { code, message ->
        }
    }

    fun onDialogConfirm() {
        uiState.isError.value = false
    }

    fun onDialogDismiss() {
        uiState.isError.value = false
    }

    fun logOut() {
        viewModelScope.launch {
            sharedPreferencesProvider.cleanup()
            isLogin.value = IsLogin(false)
        }
    }

    private fun getValidPhone(phone: String): String {
        val updatedPhone =
            phone.addCharAtIndex(')', 3)
                .addCharAtIndex('-', 7)
                .addCharAtIndex('-', 10)
        return "+7($updatedPhone"
    }

    fun hasSavedCredentials(): Boolean{
        return sharedPreferencesProvider.hasSavedCredentials()
    }
}