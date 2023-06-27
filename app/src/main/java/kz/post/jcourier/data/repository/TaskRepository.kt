package kz.post.jcourier.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.post.jcourier.common.BaseApiResponse
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.data.api.TaskApiService
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.data.model.task.TaskStatusId
import kz.post.jcourier.data.model.task.TaskIdReason
import kz.post.jcourier.data.model.task.TaskIdSms
import javax.inject.Inject

@ActivityRetainedScoped
class TaskRepository @Inject constructor(
    private val taskApiService: TaskApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun getTaskList(): NetworkResult<List<Task>> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.getTaskList()
            }
        }
    }

    suspend fun getArchiveTaskList(): NetworkResult<List<Task>> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.getArchiveTaskList()
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
}