package com.example.taskmanagerapp.data.remote


import com.example.taskmanagerapp.domain.model.Task
import retrofit2.http.GET

interface TaskApi {
    @GET("tasks")
    suspend fun getTasks(): List<Task>
}
