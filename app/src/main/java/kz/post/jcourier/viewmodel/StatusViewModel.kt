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
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.data.model.auth.TokenModel
import kz.post.jcourier.data.model.shift.Shift
import kz.post.jcourier.data.repository.ShiftRepository
import kz.post.jcourier.data.sharedprefs.SharedPreferencesProvider
import javax.inject.Inject

data class StatusState(
    val user: MutableState<TokenModel?> = mutableStateOf(null),
    var isError: MutableState<Boolean> = mutableStateOf(false),
)

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val loginRepository: ShiftRepository,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    val isLogin: MutableState<IsLogin>
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(StatusState())

    init {
        viewModelScope.launch {
            isLogin.value = IsLogin(!sharedPreferencesProvider.accessToken.isNullOrEmpty())
        }
    }

    fun setShift(shift: Shift) = viewModelScope.launch {
        when (val result = loginRepository.setStatus("3369850", Shift.ON_SHIFT)) {
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
}