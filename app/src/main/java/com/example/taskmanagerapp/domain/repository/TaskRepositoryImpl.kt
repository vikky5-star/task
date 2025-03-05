package com.example.taskmanagerapp.domain.repository


import com.example.taskmanagerapp.data.local.TaskDao
import com.example.taskmanagerapp.domain.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl  @Inject constructor(private val dao: TaskDao) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> = dao.getAllTasks()
    override suspend fun insertTask(task: Task) = dao.insertTask(task)
    override suspend fun updateTask(task: Task) = dao.updateTask(task)
    override suspend fun deleteTask(task: Task) = dao.deleteTask(task)
}
