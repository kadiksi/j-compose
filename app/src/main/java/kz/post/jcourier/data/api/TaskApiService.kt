package kz.post.jcourier.data.api

import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.data.model.task.TaskStatusId
import kz.post.jcourier.data.model.task.TaskIdReason
import kz.post.jcourier.data.model.task.TaskIdSms
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApiService {

    //active tasks
    @GET("gw/jpost-courier/api/public/v1/task/active")
    suspend fun getTaskList(): Response<List<Task>>

    //archive tasks
    @GET("gw/jpost-courier/api/public/v1/task/complete")
    suspend fun getArchiveTaskList(): Response<List<Task>>

    @GET("gw/jpost-courier/api/public/v1/task/{id}")
    suspend fun getTaskById(@Path("id") id: Int): Response<Task>

    @POST("gw/jpost-courier/api/public/v1/task/submit")
    suspend fun setStatus(@Body taskStatusId: TaskStatusId): Response<Task>

    //Complete task
    @POST("gw/jpost-courier/api/public/v1/task/complete")
    suspend fun completeTask(@Body task: TaskIdSms): Response<Task>

    //Cancel task
    @POST("gw/jpost-courier/api/public/v1/task/cancel")
    suspend fun cancelTask(@Body task: TaskIdReason): Response<Task>
}
