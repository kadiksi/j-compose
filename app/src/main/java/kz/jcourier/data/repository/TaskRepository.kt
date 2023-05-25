package kz.jcourier.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kz.jcourier.common.BaseApiResponse
import kz.jcourier.common.NetworkResult
import kz.jcourier.data.api.TaskApiService
import kz.jcourier.data.model.task.Task
import kz.jcourier.data.model.task.TaskId
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

    suspend fun getTaskById(id: Int): NetworkResult<Task> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.getTaskById(id)
            }
        }
    }

    suspend fun startTask(task: TaskId): NetworkResult<Task> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.startTask(task)
            }
        }
    }

    suspend fun pickupTask(task: TaskId): NetworkResult<Task> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.pickupTask(task)
            }
        }
    }
    suspend fun deliverTask(task: TaskId): NetworkResult<Task> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.deliverTask(task)
            }
        }
    }

    suspend fun confirmTask(task: TaskId): NetworkResult<Task> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.confirmTask(task)
            }
        }
    }

    suspend fun completeTask(task: TaskId): NetworkResult<Task> {
        return withContext(defaultDispatcher) {
            safeApiCall {
                taskApiService.completeTask(task)
            }
        }
    }
}