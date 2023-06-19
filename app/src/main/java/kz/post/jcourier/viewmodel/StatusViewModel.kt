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
    val shift: MutableState<Shift> = mutableStateOf(Shift.FREE),
    var isError: MutableState<Boolean> = mutableStateOf(false),
)

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val loginRepository: ShiftRepository,
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(StatusState())

    init {
        getCourierShift()
    }

    fun setShift(shift: Shift) = viewModelScope.launch {
        when (val result = loginRepository.setStatus(shift)) {
            is NetworkResult.Success -> {
                result.data.let {
                    uiState.shift.value = it.status
                    uiState.isError.value = false
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

    private fun getCourierShift() = viewModelScope.launch {
        when (val result = loginRepository.getStatus()) {
            is NetworkResult.Success -> {
                result.data.let {
                    uiState.shift.value = it.status
                    uiState.isError.value = false
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
}