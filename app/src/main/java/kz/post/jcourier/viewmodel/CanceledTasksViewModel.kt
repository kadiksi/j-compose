package kz.post.jcourier.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import kz.post.jcourier.data.model.task.CanceledNotification
import kz.post.jcourier.data.repository.TaskRepository
import javax.inject.Inject

data class CanceledTasksState(
    var isError: MutableState<Boolean> = mutableStateOf(false),
    var taskList: MutableState<List<CanceledNotification>> = mutableStateOf(emptyList()),
    var isRefreshing: MutableState<Boolean> = mutableStateOf(false),
    var canceledTaskCount: MutableState<Int> = mutableIntStateOf(0),
    var sort: MutableState<List<String>> = mutableStateOf(listOf("createdDate", "DESC")),
)

@HiltViewModel
class CanceledTasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(CanceledTasksState())

    init {
        getCanceledNotificationList()
    }

    fun refresh() = viewModelScope.launch {
        getCanceledNotificationList()
    }

    private fun getCanceledNotificationList() = viewModelScope.launch {
        uiState.isRefreshing.value = true
        taskRepository.getForCancellation(uiState.sort.value).onSuccess {
            uiState.isRefreshing.value = false
            it.let {
                uiState.taskList.value = it.content
                uiState.canceledTaskCount.value = it.unreadMessages
            }
        }.onError { _, message ->
            uiState.isError.value = true
            uiState.isRefreshing.value = false
        }
    }
}