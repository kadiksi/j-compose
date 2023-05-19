package kz.jcourier.data.api

import kz.jcourier.data.model.task.Task
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TaskApiService {

    @GET("gw/jpost-courier/api/public/v1/task")
    suspend fun getTaskList(): Response<List<Task>>

    @GET("gw/jpost-courier/api/public/v1/task/{id}")
    suspend fun getTaskById(@Path("id") id: Int): Response<Task>
}
