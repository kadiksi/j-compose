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
import kz.post.jcourier.data.model.error.ErrorModel
import kz.post.jcourier.data.model.task.*
import kz.post.jcourier.data.repository.TaskRepository
import javax.inject.Inject

data class TaskState(
    var isLoading: MutableState<Boolean> = mutableStateOf(false),
    var isError: MutableState<ErrorModel> = mutableStateOf(ErrorModel()),
    var isSmsDialog: MutableState<Boolean> = mutableStateOf(false),
    var isCancelReasonDialog: MutableState<Boolean> = mutableStateOf(false),
    var isCallVariantDialog: MutableState<Boolean> = mutableStateOf(false),
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

    private fun getTask(id: Long) = viewModelScope.launch {
        showLoadingDialog()
        taskRepository.getTaskById(id).onSuccess {
            hideLoadingDialog()
            it.let {
                uiState.task.value = it
            }
        }.onError { _, message ->
            hideLoadingDialog()
            uiState.isError.value = ErrorModel(true, message)
        }
    }

    fun setStatus(taskId: Long, status : TaskStatus) = viewModelScope.launch {
        showLoadingDialog()
        when (val result = taskRepository.setStatus(TaskStatusId(taskId, status))) {
            is NetworkResult.Success -> {
                hideLoadingDialog()
                result.data.let {
                    uiState.task.value = it
                }
            }
            is NetworkResult.Error -> {
                hideLoadingDialog()
                uiState.isError.value.isError = true
            }
            else -> {
                hideLoadingDialog()
                uiState.isError.value.isError = true
            }
        }
    }

    private fun completeTask(taskId: Long, sms: String) = viewModelScope.launch {
        showLoadingDialog()
        when (val result = taskRepository.completeTask(TaskIdSms(taskId, sms))) {
            is NetworkResult.Success -> {
                hideLoadingDialog()
                result.data.let {
                    uiState.task.value = it
                }
            }
            is NetworkResult.Error -> {
                hideLoadingDialog()
                uiState.isError.value.isError = true
            }
            else -> {
                hideLoadingDialog()
                uiState.isError.value.isError = true
            }
        }
    }

    private fun cancelTask(id: Long, reason: String, cancelReasonOther: String?) =
        viewModelScope.launch {
            val otherReason: String? = if (cancelReasonOther?.isEmpty() == true) {
                null
            } else {
                cancelReasonOther
            }
            showLoadingDialog()
            taskRepository.cancelTask(TaskIdReason(id, reason, otherReason)).onSuccess {
                hideLoadingDialog()
                it.let {
                    uiState.task.value = it
                }
            }.onError { _, message ->
                hideLoadingDialog()
                uiState.isError.value = ErrorModel(true, message)
            }
        }

    private fun taskCall(taskId: Long, direction: CallDto) = viewModelScope.launch {
        showLoadingDialog()
        taskRepository.callTask(TaskCallEvent(taskId = taskId, direction = direction)).onSuccess {
            hideLoadingDialog()
        }.onError { _, message ->
            hideLoadingDialog()
            uiState.isError.value = ErrorModel(true, message)
        }
//        when (val result = taskRepository.completeTask(TaskIdSms(taskId, sms))) {
//            is NetworkResult.Success -> {
//                result.data.let {
//                    uiState.task.value = it
//                }
//            }
//            is NetworkResult.Error -> {
//                uiState.isError.value.isError = true
//            }
//            else -> {
//                uiState.isError.value.isError = true
//            }
//        }
    }

    fun onConfirmWithSmsDialog(taskId: Long, sms: String) {
        dismissSmsDialog()
        completeTask(taskId, sms)
    }

    fun onCallVariantDialog(taskId: Long, direction: CallDto) {
        hideCallVariantDialog()
        taskCall(taskId, direction)
    }

    fun onCancelTaskDialog(taskId: Long, reasonIndex: Int, reason: String, cancelReasonOther: String?) {
        hideCancelReasonDialog()
        cancelTask(taskId, reason, cancelReasonOther)
    }

    fun showCallVariantDialog() {
        uiState.isCallVariantDialog.value = true
    }

    fun hideCallVariantDialog() {
        uiState.isCallVariantDialog.value = false
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

    private fun showLoadingDialog(){
        uiState.isLoading.value = true
    }

    private fun hideLoadingDialog(){
        uiState.isLoading.value = false
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