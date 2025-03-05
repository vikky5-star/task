package com.example.taskmanagerapp.presentation.viewmodel



import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerapp.domain.model.Task
import com.example.taskmanagerapp.domain.repository.TaskRepository
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    private val firebaseAnalytics = Firebase.analytics


    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            repository.getTasks().collect { _tasks.value = it }
        }
    }

    fun addTask(task: Task) = viewModelScope.launch { repository.insertTask(task) }
    fun updateTask(task: Task) = viewModelScope.launch { repository.updateTask(task) }
    fun deleteTask(task: Task) = viewModelScope.launch { repository.deleteTask(task) }

    fun trackEvent(eventName: String,params:Bundle) {
        firebaseAnalytics.logEvent(eventName, params)
    }

    fun crashApp() {
        throw RuntimeException("Test Crash") // Will be reported in Firebase
    }
}
