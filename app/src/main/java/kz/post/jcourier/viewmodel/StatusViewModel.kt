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
import kz.post.jcourier.common.onError
import kz.post.jcourier.common.onSuccess
import kz.post.jcourier.data.model.auth.TokenModel
import kz.post.jcourier.data.model.error.ErrorModel
import kz.post.jcourier.data.model.shift.Shift
import kz.post.jcourier.data.repository.ShiftRepository
import kz.post.jcourier.data.sharedprefs.SharedPreferencesProvider
import javax.inject.Inject

data class StatusState(
    val shift: MutableState<Shift> = mutableStateOf(Shift.FREE),
    var isError: MutableState<ErrorModel> = mutableStateOf(ErrorModel()),
)

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val loginRepository: ShiftRepository,
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(StatusState())

    fun setShift(shift: Shift) = viewModelScope.launch {
        loginRepository.setStatus(shift).onSuccess {
            it.let {
                uiState.shift.value = it.status
            }
        }.onError{code, message ->
            uiState.isError.value = ErrorModel(true, message)
        }
    }

    fun getCourierShift() = viewModelScope.launch {
        loginRepository.getStatus().onSuccess {
            it.let {
                uiState.shift.value = it.status
            }
        }.onError{code, message ->
            uiState.isError.value = ErrorModel(true, message)
        }
    }

    fun onDialogConfirm() {
        uiState.isError.value = ErrorModel()
    }
}