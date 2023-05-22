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
import kz.jcourier.data.model.task.Task
import kz.jcourier.data.model.task.TaskId
import kz.jcourier.data.repository.TaskRepository
import javax.inject.Inject

data class TaskState(
    var isError: MutableState<Boolean> = mutableStateOf(false),
    var task: MutableState<Task> = mutableStateOf(Task()),
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
        when (val result = taskRepository.getTaskById(id)) {
            is NetworkResult.Success -> {
                result.data?.let {
                    uiState.task.value = it
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

    fun startTask(taskId: Int, courierId: Int) = viewModelScope.launch {
        when (val result = taskRepository.startTask(TaskId(taskId, courierId))) {
            is NetworkResult.Success -> {
                result.data?.let {
                    uiState.task.value = it
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