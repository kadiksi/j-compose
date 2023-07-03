package kz.post.jcourier.data.api

import kz.post.jcourier.data.model.task.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TaskApiService {

    //active tasks
    @GET("gw/jpost-courier/api/public/v1/task")
    suspend fun getTaskList(@Query("status") status: TaskStatus): Response<List<Task>>

    //archive tasks
    @GET("gw/jpost-courier/api/public/v1/task/complete")
    suspend fun getArchiveTaskList(@Query("status") status: TaskStatus): Response<List<Task>>

    @GET("gw/jpost-courier/api/public/v1/task/{id}")
    suspend fun getTaskById(@Path("id") id: Long): Response<Task>

    @POST("gw/jpost-courier/api/public/v1/task/submit")
    suspend fun setStatus(@Body taskStatusId: TaskStatusId): Response<Task>

    //Complete task
    @POST("gw/jpost-courier/api/public/v1/task/complete")
    suspend fun completeTask(@Body task: TaskIdSms): Response<Task>

    //Cancel task
    @POST("gw/jpost-courier/api/public/v1/task/cancel")
    suspend fun cancelTask(@Body task: TaskIdReason): Response<Task>

    @POST("gw/jpost-courier/api/public/v1/event/call")
    suspend fun callTask(@Body task: TaskCallEvent): Response<Boolean>
}
