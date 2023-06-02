package kz.post.jcourier.viewmodel

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
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.common.onError
import kz.post.jcourier.common.onSuccess
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.data.model.task.TaskId
import kz.post.jcourier.data.repository.TaskRepository
import javax.inject.Inject

data class TaskState(
    var isError: MutableState<ErrorModel> = mutableStateOf(ErrorModel()),
    var task: MutableState<Task> = mutableStateOf(Task()),
)

data class ErrorModel(
    var isError: Boolean = false,
    var text: String = ""
)

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(TaskState())
    private val args = savedStateHandle.get<Task>("task")

    init {
        args?.id?.let { getTask(it) }
    }

    private fun getTask(id: Int) = viewModelScope.launch {
        taskRepository.getTaskById(id).onSuccess {
            it.let {
                uiState.task.value = it
            }
        }.onError {
            uiState.isError.value = ErrorModel(true, it.toString())
        }
//        when (val result = taskRepository.getTaskById(id)) {
//            is NetworkResult.Success -> {
//                result.data.let {
//                    uiState.task.value = it
//                }
//            }
//            is NetworkResult.Error -> {
//                uiState.isError.value = ErrorModel(true, result.
//            }
//            else -> {
//                uiState.isError.value.isError = true
//            }
//        }
    }

    fun startTask(taskId: Int) = viewModelScope.launch {
        when (val result = taskRepository.startTask(TaskId(taskId))) {
            is NetworkResult.Success -> {
                result.data.let {
                    uiState.task.value = it
                }
            }
            is NetworkResult.Error -> {
                uiState.isError.value.isError = true
            }
            else -> {
                uiState.isError.value.isError = true
            }
        }
    }

    fun pickupTask(taskId: Int) = viewModelScope.launch {
        when (val result = taskRepository.pickupTask(TaskId(taskId))) {
            is NetworkResult.Success -> {
                result.data.let {
                    uiState.task.value = it
                }
            }
            is NetworkResult.Error -> {
                uiState.isError.value.isError = true
            }
            else -> {
                uiState.isError.value.isError = true
            }
        }
    }

    fun deliverTask(taskId: Int) = viewModelScope.launch {
        when (val result = taskRepository.deliverTask(TaskId(taskId))) {
            is NetworkResult.Success -> {
                result.data.let {
                    uiState.task.value = it
                }
            }
            is NetworkResult.Error -> {
                uiState.isError.value.isError = true
            }
            else -> {
                uiState.isError.value.isError = true
            }
        }
    }

    fun confirmTask(taskId: Int) = viewModelScope.launch {
        when (val result = taskRepository.confirmTask(TaskId(taskId))) {
            is NetworkResult.Success -> {
                result.data.let {
                    uiState.task.value = it
                }
            }
            is NetworkResult.Error -> {
                uiState.isError.value.isError = true
            }
            else -> {
                uiState.isError.value.isError = true
            }
        }
    }

    fun completeTask(taskId: Int) = viewModelScope.launch {
        when (val result = taskRepository.completeTask(TaskId(taskId))) {
            is NetworkResult.Success -> {
                result.data.let {
                    uiState.task.value = it
                }
            }
            is NetworkResult.Error -> {
                uiState.isError.value.isError = true
            }
            else -> {
                uiState.isError.value.isError = true
            }
        }
    }

    fun onDialogConfirm() {
        uiState.isError.value.isError = false
    }

    fun onDialogDismiss() {
        uiState.isError.value.isError = false
    }
}