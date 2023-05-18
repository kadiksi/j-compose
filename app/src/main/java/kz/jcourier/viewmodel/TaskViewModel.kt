package kz.jcourier.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.jcourier.common.NetworkResult
import kz.jcourier.data.model.auth.TokenModel
import kz.jcourier.data.model.task.Task
import kz.jcourier.data.repository.LoginRepository
import javax.inject.Inject

data class TaskState(
    var isError: MutableState<Boolean> = mutableStateOf(false),
    var taskList: MutableState<List<Task>> = mutableStateOf(emptyList()),
)

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(HomeState())
    private val args = savedStateHandle.get<Task>("task")

    init {
        args?.id?.let { getTask(it) }
        Log.e("IDD", "${args?.id}")
    }

    fun getTask(id: Int) = viewModelScope.launch {
        Log.e("IDD", "${id}")
//        when (val result = loginRepository.getUserRoleList()) {
//            is NetworkResult.Success -> {
//                result.data?.let {
//                    uiState.taskList.value = it
//                }
//            }
//            is NetworkResult.Error -> {
//                Log.e("ER", "${result.data}")
//                uiState.isError.value = true
//            }
//            else -> {
//                Log.e("ER", "Else")
//                uiState.isError.value = true
//            }
//        }
    }
}