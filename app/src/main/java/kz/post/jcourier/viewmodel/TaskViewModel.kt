package kz.post.jcourier.viewmodel

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.post.jcourier.common.onError
import kz.post.jcourier.common.onSmsError
import kz.post.jcourier.common.onSuccess
import kz.post.jcourier.data.model.error.ErrorModel
import kz.post.jcourier.data.model.task.*
import kz.post.jcourier.data.repository.TaskRepository
import kz.post.jcourier.utils.parseDateTime
import kz.post.jcourier.utils.toMinSec
import kz.post.jcourier.utils.toMultipart
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

data class TaskState(
    var isLoading: MutableState<Boolean> = mutableStateOf(false),
    var isError: MutableState<ErrorModel> = mutableStateOf(ErrorModel()),
    var isSmsDialog: MutableState<Boolean> = mutableStateOf(false),
    var isCancelReasonDialog: MutableState<Boolean> = mutableStateOf(false),
    var isCallVariantDialog: MutableState<Boolean> = mutableStateOf(false),
    var fileList: MutableState<MutableList<File>> = mutableStateOf(mutableListOf()),
    var task: MutableState<Task> = mutableStateOf(Task()),
    var isRefreshing: MutableState<Boolean> = mutableStateOf(false),
    var timer: MutableState<SmsTimer> = mutableStateOf(SmsTimer()),
)

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(TaskState())
    private val args = savedStateHandle.get<Long>("taskId")

    init {
        getTask()
    }

    fun getTask() = viewModelScope.launch {
        args?.let {
            showLoadingDialog()
            taskRepository.getTaskById(it).onSuccess { task ->
                hideLoadingDialog()
                uiState.task.value = task
            }.onError { _, message ->
                hideLoadingDialog()
                uiState.isError.value = ErrorModel(true, message)
            }
        }
    }

    fun setStatus(taskId: Long, status : TaskStatus) = viewModelScope.launch {
        showLoadingDialog()
        taskRepository.setStatus(TaskStatusId(taskId, status)).onSuccess {
            hideLoadingDialog()
            it.let {
                uiState.task.value = it
                if(it.actions.contains(TaskStatus.CONFIRM) && it.finalRoute == true){
                    setTimer(it.nextSendTime)
                }
            }
        }.onError { _, message ->
            hideLoadingDialog()
            uiState.isError.value = ErrorModel(true, message)
        }.onSmsError { _, message, date ->
            hideLoadingDialog()
            uiState.isError.value = ErrorModel(true, message)
            if(uiState.task.value.actions.contains(TaskStatus.CONFIRM) && uiState.task.value.finalRoute == true){
                setTimer(parseDateTime(date))
            }
        }
    }

    fun completeWithFiles(taskId: Long, sms: String?){
        val list = _images.value ?: emptyList()
        if(list.size < 5){
            uiState.isError.value = ErrorModel(true, "Выберите минимум 5 фото")
            return
        }
        completeTask(taskId, sms)
    }
    fun completeTask(taskId: Long, sms: String?) = viewModelScope.launch {
        showLoadingDialog()
        taskRepository.completeTask(TaskIdSms(taskId, sms)).onSuccess {
            hideLoadingDialog()
            it.let {
                uiState.task.value = it
                onRemoveImagesFromMemory()
            }
//            uploadFiles(context, taskId)
        }.onError { _, message ->
            hideLoadingDialog()
            uiState.isError.value = ErrorModel(true, message)
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
    }

    private val _images = MutableLiveData<List<File>>(listOf())
    val images: LiveData<List<File>> get() = _images

    private fun uploadFiles(context: Context, taskId: Long, sms: String, type: FileType) = viewModelScope.launch {
        val parts = getParts(context)
        showLoadingDialog()
        taskRepository.uploadFiles(taskId, type, parts).onSuccess {
            completeTask(taskId, sms)
            hideLoadingDialog()
        }.onError { _, message ->
            hideLoadingDialog()
            uiState.isError.value = ErrorModel(true, message)
        }
    }

    private fun uploadFiles(context: Context, taskId: Long, status : TaskStatus, type: FileType) = viewModelScope.launch {
        val parts = getParts(context)
        showLoadingDialog()
        taskRepository.uploadFiles(taskId, type, parts).onSuccess {
            setStatus(taskId, status)
            hideLoadingDialog()
            onRemoveImagesFromMemory()
        }.onError { _, message ->
            hideLoadingDialog()
            uiState.isError.value = ErrorModel(true, message)
        }
    }

    private fun getParts(context: Context): List<MultipartBody.Part>{
        val list =  ArrayList<MultipartBody.Part>()
        _images.value?.forEach {
            it.toMultipart(context, "file", "${Date().time}_photo.jpg")?.let { it1 -> list.add(it1) }
        }
        return list
    }
    fun onRemoveImageFile(index: Int) {
        val imageFiles = _images.value ?: return
        val file = imageFiles[index]
        _images.value = imageFiles.filter { it != file }
        uiState.fileList.value = _images.value?.toMutableList() ?: mutableListOf()
        file.delete()
    }

    fun onAddImageFile(file: File) {
        val imageFiles = _images.value?.toMutableList() ?: mutableListOf()
        imageFiles.add(file)
        uiState.fileList.value = imageFiles
        _images.value = imageFiles
    }

    private fun onRemoveImagesFromMemory() {
        val imageFiles = _images.value
        imageFiles?.forEach { it.delete() }
        _images.value = listOf()
        uiState.fileList.value = _images.value?.toMutableList() ?: mutableListOf()
    }

    fun onConfirmWithSmsDialog(context: Context,taskId: Long, sms: String) {
        dismissSmsDialog()
        uploadFiles(context, taskId, sms, FileType.CUSTOMER)
    }

    fun onCallVariantDialog(taskId: Long, direction: CallDto) {
        hideCallVariantDialog()
        taskCall(taskId, direction)
    }

    fun onCancelTaskDialog(taskId: Long, reason: String, cancelReasonOther: String?) {
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
        val list = _images.value ?: emptyList()
        if(list.size < 5){
            uiState.isError.value = ErrorModel(true, "Выберите минимум 5 фото")
            return
        }
        uiState.isSmsDialog.value = true
    }

    fun uploadPickUpFiles(taskId: Long, context: Context) {
        val list = _images.value ?: emptyList()
        if(list.size < 5){
            uiState.isError.value = ErrorModel(true, "Выберите минимум 5 фото")
            return
        }
        uploadFiles(context,taskId, TaskStatus.PICK_UP, FileType.MERCHANT)
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
    private fun setTimer(nextSendTime: Date?) {
        nextSendTime?.let { it2 ->
            uiState.timer.value = SmsTimer(canSendSms = false)
            startTimer(it2)
        }
    }

    var timer: CountDownTimer? = null
    private fun startTimer(endDate: Date) {
        timer = object : CountDownTimer(endDate.time - Date().time, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val time = toMinSec(Date(millisUntilFinished))
                uiState.timer.value = SmsTimer(time,false)
            }

            override fun onFinish() {
                uiState.timer.value = SmsTimer("", true)
            }
        }.start()
    }
}