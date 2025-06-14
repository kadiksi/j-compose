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
import kz.post.jcourier.common.onError
import kz.post.jcourier.common.onSuccess
import kz.post.jcourier.data.model.error.ErrorModel
import kz.post.jcourier.data.model.shift.CourierModel
import kz.post.jcourier.data.model.shift.Shift
import kz.post.jcourier.data.repository.ShiftRepository
import kz.post.jcourier.data.sharedprefs.SharedPreferencesProvider
import javax.inject.Inject

data class StatusState(
    val shift: MutableState<CourierModel> = mutableStateOf(CourierModel()),
    var isError: MutableState<ErrorModel> = mutableStateOf(ErrorModel()),
)

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val loginRepository: ShiftRepository,
    val isLogin: MutableState<IsLogin>,
    val user: MutableState<CourierModel>,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(StatusState())

    init {
        isLogin.value = IsLogin(true)
    }

    fun setShift(shift: Shift) = viewModelScope.launch {
        loginRepository.setStatus(shift).onSuccess {
            it.let {
                user.value = it
                uiState.shift.value = it
            }
        }.onError{code, message ->
            uiState.isError.value = ErrorModel(true, message)
        }
    }

    fun getCourierShift() = viewModelScope.launch {
        onDialogConfirm()
        isLogin.value = IsLogin(true)
        loginRepository.getStatus().onSuccess {
            it.let {
                user.value = it
                uiState.shift.value = it
            }
        }.onError{code, message ->
            if(message.contains("401")) {
                isLogin.value = IsLogin(false)
//                sharedPreferencesProvider.cleanup()
                onDialogConfirm()
            }
            uiState.isError.value = ErrorModel(true, message)
        }
    }

    fun onDialogConfirm() {
        uiState.isError.value = ErrorModel()
    }
}