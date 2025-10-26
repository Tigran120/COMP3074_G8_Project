package ca.gbc.comp3074.quicktasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.gbc.comp3074.quicktasks.model.Task
import ca.gbc.comp3074.quicktasks.ui.screens.AddTaskScreen
import ca.gbc.comp3074.quicktasks.ui.screens.TaskDetailScreen
import ca.gbc.comp3074.quicktasks.ui.screens.TaskListScreen
import ca.gbc.comp3074.quicktasks.ui.screens.WelcomeScreen
import ca.gbc.comp3074.quicktasks.ui.theme.QuickTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickTasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuickTasksApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun QuickTasksApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    
    val sampleTasks = remember {
        listOf(
            Task(
                id = 1,
                title = "Task 1",
                description = "Description 1",
                category = "School",
                dueDate = System.currentTimeMillis() + 86400000,
                isCompleted = false,
                createdAt = System.currentTimeMillis() - 172800000
            ),
            Task(
                id = 2,
                title = "Task 2",
                description = "Description 2",
                category = "Personal",
                isCompleted = true,
                createdAt = System.currentTimeMillis() - 86400000
            ),
            Task(
                id = 3,
                title = "Task 3",
                description = "Description 3",
                category = "Work",
                dueDate = System.currentTimeMillis() - 86400000,
                isCompleted = false,
                createdAt = System.currentTimeMillis() - 259200000
            ),
            Task(
                id = 4,
                title = "Task 4",
                description = "Description 4",
                category = "School",
                dueDate = System.currentTimeMillis() + 604800000,
                isCompleted = false,
                createdAt = System.currentTimeMillis() - 432000000
            ),
            Task(
                id = 5,
                title = "Task 5",
                description = "Description 5",
                category = "Personal",
                isCompleted = false,
                createdAt = System.currentTimeMillis() - 345600000
            )
        )
    }
    
    var tasks by remember { mutableStateOf(sampleTasks) }
    var currentTask by remember { mutableStateOf<Task?>(null) }
    var editingTask by remember { mutableStateOf<Task?>(null) }
    
    NavHost(
        navController = navController,
        startDestination = "welcome",
        modifier = modifier
    ) {
        composable("welcome") {
            WelcomeScreen(
                onGetStartedClick = {
                    navController.navigate("task_list")
                }
            )
        }
        
        composable("task_list") {
            TaskListScreen(
                tasks = tasks,
                onTaskClick = { task: Task ->
                    currentTask = task
                    navController.navigate("task_detail")
                },
                onTaskToggleComplete = { task: Task ->
                    tasks = tasks.map { 
                        if (it.id == task.id) {
                            it.copy(isCompleted = !it.isCompleted)
                        } else {
                            it
                        }
                    }
                },
                onAddTaskClick = {
                    navController.navigate("add_task")
                }
            )
        }
        
        composable("add_task") {
            AddTaskScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveTask = { title, description, dueDate, category ->
                    val newTask = Task(
                        id = tasks.maxOfOrNull { it.id }?.plus(1) ?: 1,
                        title = title,
                        description = description,
                        dueDate = dueDate,
                        category = category,
                        isCompleted = false,
                        createdAt = System.currentTimeMillis()
                    )
                    tasks = tasks + newTask
                    navController.popBackStack()
                }
            )
        }
        
        composable("edit_task") {
            editingTask?.let { task ->
                AddTaskScreen(
                    existingTask = task,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSaveTask = { title, description, dueDate, category ->
                        val updatedTask = task.copy(
                            title = title,
                            description = description,
                            dueDate = dueDate,
                            category = category
                        )
                        tasks = tasks.map { if (it.id == task.id) updatedTask else it }
                        navController.popBackStack()
                    }
                )
            }
        }
        
        composable("task_detail") {
            currentTask?.let { task ->
                TaskDetailScreen(
                    task = task,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditTask = { taskToEdit: Task ->
                        editingTask = taskToEdit
                        navController.navigate("edit_task")
                    },
                    onDeleteTask = { taskToDelete: Task ->
                        tasks = tasks.filter { it.id != taskToDelete.id }
                        navController.popBackStack()
                    },
                    onToggleComplete = { taskToToggle: Task ->
                        tasks = tasks.map { 
                            if (it.id == taskToToggle.id) {
                                it.copy(isCompleted = !it.isCompleted)
                            } else {
                                it
                            }
                        }
                    }
                )
            }
        }
    }
}
