package kz.post.jcourier.data.api

import kz.post.jcourier.data.model.task.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface TaskApiService {

    //active tasks
    @GET("gw/jpost-courier/api/public/v1/task")
    suspend fun getTaskList(@Query("status") status: TaskStatus): Response<List<Task>>

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

    @Multipart
    @POST("gw/jpost-courier/api/public/v1/event/upload")
    suspend fun uploadFiles(
        @Query("taskId") taskId: Long,
        @Part file: List<MultipartBody.Part>,
    ): Response<Boolean>
}
