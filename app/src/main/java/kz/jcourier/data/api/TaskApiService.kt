package kz.jcourier.data.api

import kz.jcourier.data.model.task.Task
import kz.jcourier.data.model.task.TaskId
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApiService {

    @GET("gw/jpost-courier/api/public/v1/task")
    suspend fun getTaskList(): Response<List<Task>>

    @GET("gw/jpost-courier/api/public/v1/task/{id}")
    suspend fun getTaskById(@Path("id") id: Int): Response<Task>

    @POST("gw/jpost-courier/api/public/v1/task/start")
    suspend fun startTask(@Body task: TaskId): Response<Task>

    @POST("gw/jpost-courier/api/public/v1/task/pickup")
    suspend fun pickupTask(@Body task: TaskId): Response<Task>

    @POST("gw/jpost-courier/api/public/v1/task/deliver")
    suspend fun deliverTask(@Body task: TaskId): Response<Task>

    @POST("gw/jpost-courier/api/public/v1/task/confirm")
    suspend fun confirmTask(@Body task: TaskId): Response<Task>

    @POST("gw/jpost-courier/api/public/v1/task/complete")
    suspend fun completeTask(@Body task: TaskId): Response<Task>
}