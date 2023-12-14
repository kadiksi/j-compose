package kz.post.jcourier.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.post.jcourier.common.BaseApiResponse
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.data.api.TaskApiService
import kz.post.jcourier.data.model.task.*
import okhttp3.MultipartBody
import javax.inject.Inject

@ActivityRetainedScoped
class TaskRepository @Inject constructor(
    private val taskApiService: TaskApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun getTaskList(): NetworkResult<List<Task>> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.getTaskList(TaskStatus.ACTIVE)
            }
        }
    }

    suspend fun getArchiveTaskList(): NetworkResult<List<Task>> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.getTaskList(TaskStatus.COMPLETE)
            }
        }
    }

    suspend fun getForCancellation(
        page: Int,
        size: Int,
        sortIsRead: String,
        sortCreatedDate: String
    ): NetworkResult<ForCancel> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.getForCancellation(page, size, sortIsRead, sortCreatedDate)
            }
        }
    }

    suspend fun getTaskById(id: Long): NetworkResult<Task> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.getTaskById(id)
            }
        }
    }

    suspend fun markAsRead(notificationId: Long): NetworkResult<Unit> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.markAsRead(notificationId)
            }
        }
    }

    suspend fun setStatus(task: TaskStatusId): NetworkResult<Task> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.setStatus(task)
            }
        }
    }

    suspend fun completeTask(task: TaskIdSms): NetworkResult<Task> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.completeTask(task)
            }
        }
    }

    suspend fun cancelTask(task: TaskIdReason): NetworkResult<Task> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.cancelTask(task)
            }
        }
    }
    suspend fun callTask(task: TaskCallEvent): NetworkResult<Boolean> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.callTask(task)
            }
        }
    }

    suspend fun uploadFiles(
        taskId: Long,
        type: FileType,
        file: List<MultipartBody.Part>,
    ): NetworkResult<Boolean> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.uploadFiles(taskId, type, file)
            }
        }
    }
}