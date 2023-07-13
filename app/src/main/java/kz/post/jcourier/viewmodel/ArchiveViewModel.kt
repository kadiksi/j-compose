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
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.data.repository.TaskRepository
import javax.inject.Inject

data class ArchiveState(
    var isError: MutableState<Boolean> = mutableStateOf(false),
    var taskList: MutableState<List<Task>> = mutableStateOf(emptyList()),
    var isRefreshing: MutableState<Boolean> = mutableStateOf(false)
)

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(ArchiveState())
    init {
        getTaskList()
    }

    fun refresh() = viewModelScope.launch {
        getTaskList()
    }

    private fun getTaskList() = viewModelScope.launch {
        uiState.isRefreshing.value = true
        when (val result = taskRepository.getArchiveTaskList()) {
            is NetworkResult.Success -> {
                uiState.isRefreshing.value = false
                result.data.let {
                    uiState.taskList.value = it
                }
            }
            is NetworkResult.Error -> {
                uiState.isError.value = true
                uiState.isRefreshing.value = false
            }
            else -> {
                uiState.isError.value = true
                uiState.isRefreshing.value = false
            }
        }
    }
}