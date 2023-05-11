package kz.jcourier.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kz.jcourier.utils.addCharAtIndex
import kz.jcourier.common.NetworkResult
import kz.jcourier.data.model.auth.TokenModule
import kz.jcourier.data.repository.LoginRepository
import kz.jcourier.data.sharedprefs.SharedPreferencesProvider
import javax.inject.Inject

data class LoginState(
    var isAuthorised: MutableState<Boolean> = mutableStateOf(false),
    val user: MutableState<TokenModule?> = mutableStateOf(null),
    var isError: MutableState<Boolean> = mutableStateOf(false),
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferencesProvider: SharedPreferencesProvider
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(LoginState())

    init {
        viewModelScope.launch {
            uiState.isAuthorised.value = !sharedPreferencesProvider.accessToken.isNullOrEmpty()
        }
    }

    fun login(phone: String, password: String) = viewModelScope.launch {
        if (phone.length != 10)
            return@launch
        when (val result = loginRepository.login(getValidPhone(phone), password)) {
            is NetworkResult.Success -> {
                result.data?.let {
                    it.data?.let { it1 -> sharedPreferencesProvider.setUserData(it1) }
                    uiState.user.value = it.data
                    Log.e("SUCCESS", it.data?.tokens?.auth?.token!!)
                    uiState.isError.value = false
                    uiState.isAuthorised.value = true
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
        // Continue with executing the confirmed action
    }

    fun onDialogDismiss() {
        uiState.isError.value = false
    }

    fun logOut(){
        viewModelScope.launch {
            sharedPreferencesProvider.cleanup()
            uiState.isAuthorised.value = false
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