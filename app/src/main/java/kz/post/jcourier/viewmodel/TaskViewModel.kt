package kz.post.jcourier.viewmodel

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
import kz.post.jcourier.data.model.task.*
import kz.post.jcourier.data.repository.TaskRepository
import javax.inject.Inject

data class TaskState(
    var isError: MutableState<ErrorModel> = mutableStateOf(ErrorModel()),
    var isSmsDialog: MutableState<Boolean> = mutableStateOf(false),
    var isCancelReasonDialog: MutableState<Boolean> = mutableStateOf(false),
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
        }.onError { data, message ->
            uiState.isError.value = ErrorModel(true, message)
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

    fun completeTask(taskId: Int, sms: String) = viewModelScope.launch {
        when (val result = taskRepository.completeTask(TaskIdSms(taskId, sms))) {
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

    private fun cancelTask(id: Int, reason: CancelReasonDto, cancelReasonOther: String?) =
        viewModelScope.launch {
            val otherReason: String? = if (cancelReasonOther?.isEmpty() == true) {
                null
            } else {
                cancelReasonOther
            }
            taskRepository.cancelTask(TaskIdReason(id, reason, otherReason)).onSuccess {
                it.let {
                    uiState.task.value = it
                }
            }.onError { data, message ->
                uiState.isError.value = ErrorModel(true, message)
            }
        }

    fun onConfirmWithSmsDialog(taskId: Int, sms: String) {
        dismissSmsDialog()
        completeTask(taskId, sms)
    }

    fun onCancelTaskDialog(taskId: Int, reasonIndex: Int, cancelReasonOther: String?) {
        hideCancelReasonDialog()
        cancelTask(taskId, getCancellationReason(reasonIndex), cancelReasonOther)
    }

    fun showCancelReasonDialog() {
        uiState.isCancelReasonDialog.value = true
    }

    fun hideCancelReasonDialog() {
        uiState.isCancelReasonDialog.value = false
    }

    fun showSmsDialog() {
        uiState.isSmsDialog.value = true
    }

    fun dismissSmsDialog() {
        uiState.isSmsDialog.value = false
    }

    fun onDialogConfirm() {
        uiState.isError.value = ErrorModel()
    }

    private fun getCancellationReason(reasonIndex: Int): CancelReasonDto {
        return arrayOf(
            CancelReasonDto.NA,
            CancelReasonDto.LDT,
            CancelReasonDto.AG,
            CancelReasonDto.FC,
            CancelReasonDto.O
        )[reasonIndex]
    }
}