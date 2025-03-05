package com.example.taskmanagerapp.presentation.ui

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskmanagerapp.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    navHostController: NavHostController,
    taskId: Int
) {
    val tasks = viewModel.tasks.collectAsState()
    val task = tasks.value.find { it.id == taskId }

    var taskTitle = remember { mutableStateOf(task?.title ?: "") }

    if (task == null) {
        Text("Task not found")
        return
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Edit Task") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = taskTitle.value,
                onValueChange = { taskTitle.value = it },
                label = { Text("Task Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (taskTitle.value.isNotBlank()) {
                        viewModel.updateTask(task.copy(title = taskTitle.value))
                        val params = Bundle().apply {
                            putString("task_id", task.id.toString())
                            putString("task_title", taskTitle.value)
                        }
                        viewModel.trackEvent("Task_Edited", params)
                        navHostController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Task")
            }
        }
    }
}
