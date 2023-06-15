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
import kz.post.jcourier.data.sharedprefs.SharedPreferencesProvider
import javax.inject.Inject

data class HomeState(
    var isError: MutableState<Boolean> = mutableStateOf(false),
    var taskList: MutableState<List<Task>> = mutableStateOf(emptyList()),
    var isRefreshing: MutableState<Boolean> = mutableStateOf(false)
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    val isLogin: MutableState<IsLogin>,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(HomeState())
    init {
        getTaskList()
        isLogin.value = IsLogin(true)
    }

    fun refresh() = viewModelScope.launch {
        getTaskList()
    }

    private fun getTaskList() = viewModelScope.launch {
        uiState.isRefreshing.value = true
        isLogin.value = IsLogin(true)
        when (val result = taskRepository.getTaskList()) {
            is NetworkResult.Success -> {
                uiState.isRefreshing.value = false
                result.data.let {
                    uiState.taskList.value = it
                }
            }
            is NetworkResult.Error -> {
                uiState.isError.value = true
                uiState.isRefreshing.value = false
                if(result.message.contains("401")) {
                    isLogin.value = IsLogin(false)
                    sharedPreferencesProvider.cleanup()
                }
            }
            else -> {
                uiState.isError.value = true
                uiState.isRefreshing.value = false
            }
        }
    }
}