package kz.post.jcourier.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.post.jcourier.common.onError
import kz.post.jcourier.common.onSuccess
import kz.post.jcourier.data.model.task.CanceledNotification
import kz.post.jcourier.data.repository.TaskRepository
import kz.post.jcourier.utils.paginator.ListState
import javax.inject.Inject

data class CanceledTasksState(
    var isError: MutableState<Boolean> = mutableStateOf(false),
    var isRefreshing: MutableState<Boolean> = mutableStateOf(false),
    var canceledTaskCount: MutableState<Int> = mutableIntStateOf(0),
)

@HiltViewModel
class CanceledTasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(CanceledTasksState())
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(ListState.IDLE)
    private var page by mutableIntStateOf(0)
    private val pageSize = 7
    private val sortCreatedDate = "createdDate,DESC"
    private val sortIsRead = "isRead,ASC"

    init {
        uiState.isRefreshing.value = true
        getCanceledNotificationList()
    }

    fun refresh() = viewModelScope.launch {
        page = 0
        listState = ListState.IDLE
        canPaginate = false
        uiState.isRefreshing.value = true
        getCanceledNotificationList()

    }

    val canceledTaskList = mutableStateListOf<CanceledNotification>()

    fun getCanceledNotificationList() = viewModelScope.launch {
        if (page == 0 || (page != 0 && canPaginate) && listState == ListState.IDLE) {
            listState = if (page == 0) ListState.LOADING else ListState.PAGINATING
            taskRepository.getForCancellation(page, pageSize, sortIsRead, sortCreatedDate).onSuccess {
                uiState.isRefreshing.value = false
                it.let {
                    uiState.canceledTaskCount.value = it.unreadMessages

                    canPaginate = it.content.size == pageSize

                    if (page == 0) {
                        canceledTaskList.clear()
                        canceledTaskList.addAll(it.content)
                    } else {
                        canceledTaskList.addAll(it.content)
                    }

                    listState = ListState.IDLE

                    if (canPaginate)
                        page++
                }
            }.onError { _, message ->
                uiState.isError.value = true
                uiState.isRefreshing.value = false
            }
        }
    }

    override fun onCleared() {
        page = 0
        listState = ListState.IDLE
        canPaginate = false
        super.onCleared()
    }
}