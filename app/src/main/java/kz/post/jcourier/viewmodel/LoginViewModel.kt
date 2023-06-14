package kz.post.jcourier.viewmodel

import android.util.Log
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
import kz.post.jcourier.data.model.auth.TokenModel
import kz.post.jcourier.data.repository.LoginRepository
import kz.post.jcourier.data.sharedprefs.SharedPreferencesProvider
import javax.inject.Inject

data class LoginState(
    val user: MutableState<TokenModel?> = mutableStateOf(null),
    var isError: MutableState<Boolean> = mutableStateOf(false),
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    val isLogin: MutableState<IsLogin>
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(LoginState())

    init {
        viewModelScope.launch {
            isLogin.value = IsLogin(!sharedPreferencesProvider.accessToken.isNullOrEmpty())
        }
    }

    fun login(phone: String, password: String) = viewModelScope.launch {
        if (phone.length != 10)
            return@launch
        when (val result = loginRepository.login(getValidPhone(phone), password)) {
            is NetworkResult.Success -> {
                result.data.data?.let {
                    sharedPreferencesProvider.setUserData(it)
                    uiState.user.value = it
                    uiState.isError.value = false
                    isLogin.value = IsLogin(true)
                }
            }
            is NetworkResult.Error -> {
                Log.e("ER", "${result.data}")
                uiState.isError.value = true
            }
            else -> {
                Log.e("ER", "Else")
                uiState.isError.value = true
            }
        }
    }

    fun onDialogConfirm() {
        uiState.isError.value = false
    }

    fun onDialogDismiss() {
        uiState.isError.value = false
    }

    fun logOut(){
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
}