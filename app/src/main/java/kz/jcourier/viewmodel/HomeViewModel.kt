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
import kotlinx.coroutines.launch
import kz.jcourier.common.NetworkResult
import kz.jcourier.data.model.task.Task
import kz.jcourier.data.repository.TaskRepository
import javax.inject.Inject

data class HomeState(
    var isError: MutableState<Boolean> = mutableStateOf(false),
    var taskList: MutableState<List<Task>> = mutableStateOf(emptyList()),
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(HomeState())

    init {
        getUserRoleList()
    }

    private fun getUserRoleList() = viewModelScope.launch {
        when (val result = taskRepository.getTaskList()) {
            is NetworkResult.Success -> {
                result.data?.let {
                    uiState.taskList.value = it
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