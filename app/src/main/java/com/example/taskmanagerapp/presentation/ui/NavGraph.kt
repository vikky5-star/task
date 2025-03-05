package com.example.taskmanagerapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavHostController

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "task_list") {
        composable("task_list") { TaskListScreen(navHostController = navController) }
        composable("add_task") { AddTaskScreen(navHostController = navController) }
        composable(
            "edit_task/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            EditTaskScreen(navHostController = navController, taskId = taskId)
        }
    }
}
