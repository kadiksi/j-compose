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
import kz.post.jcourier.common.onError
import kz.post.jcourier.common.onSuccess
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.data.repository.TaskRepository
import javax.inject.Inject

data class StatisticsState(
    var isError: MutableState<Boolean> = mutableStateOf(false),
    var amount: MutableState<String> = mutableStateOf(""),
    var dateFrom: MutableState<String> = mutableStateOf(""),
    var dateTo: MutableState<String> = mutableStateOf(""),
)

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(StatisticsState())

    fun getStats() {
        if (uiState.dateFrom.value.isNotEmpty() && uiState.dateTo.value.isNotEmpty())
            getStatisticsList(uiState.dateFrom.value, uiState.dateTo.value)
    }

    private fun getStatisticsList(dateFrom: String, dateTo: String) = viewModelScope.launch {
        taskRepository.getStatistics(dateFrom, dateTo).onSuccess {
            it.let {
                uiState.amount.value = it
            }
        }.onError { code, message ->
            uiState.isError.value = true
        }
    }
}