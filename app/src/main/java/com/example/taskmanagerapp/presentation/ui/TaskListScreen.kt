package com.example.taskmanagerapp.presentation.ui

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskmanagerapp.domain.model.Task
import com.example.taskmanagerapp.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val tasks by viewModel.tasks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task List") },
                actions = {
                    IconButton(onClick = { navHostController.navigate("add_task") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Task")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onComplete = { viewModel.updateTask(it.copy(isCompleted = !it.isCompleted)) },
                    onEdit = { navHostController.navigate("edit_task/${task.id}") },
                    onDelete = { viewModel.deleteTask(task) },
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onComplete: (Task) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    viewModel: TaskViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEdit() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
                if (task.isCompleted) {
                    Text(text = "Completed", color = MaterialTheme.colorScheme.primary)
                }
            }
            Row {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = {
                        onComplete(task)
                        val params = Bundle().apply {
                            putString("task_id", task.id.toString())
                            putBoolean("is_completed", it)
                        }
                        viewModel.trackEvent("Task_Completed", params)
                    }
                )
                IconButton(onClick = { onDelete() }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Task")
                }
            }
        }
    }
}
