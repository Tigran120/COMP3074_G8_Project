package ca.gbc.comp3074.quicktasks.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.gbc.comp3074.quicktasks.R
import ca.gbc.comp3074.quicktasks.model.Task
import ca.gbc.comp3074.quicktasks.ui.theme.QuickTasksTheme
import ca.gbc.comp3074.quicktasks.ui.theme.TaskBlue
import ca.gbc.comp3074.quicktasks.ui.theme.TaskGray
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onTaskClick: (Task) -> Unit,
    onTaskToggleComplete: (Task) -> Unit,
    onAddTaskClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.task_list_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                IconButton(onClick = onAddTaskClick) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Task",
                        tint = TaskBlue
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.no_tasks),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onAddTaskClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TaskBlue
                        )
                    ) {
                        Text("Add Your First Task")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onClick = { onTaskClick(task) },
                        onToggleComplete = { onTaskToggleComplete(task) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    onToggleComplete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onToggleComplete,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = if (task.isCompleted) {
                        Icons.Default.CheckCircle
                    } else {
                        Icons.Default.RadioButtonUnchecked
                    },
                    contentDescription = if (task.isCompleted) "Mark Incomplete" else "Mark Complete",
                    tint = if (task.isCompleted) TaskBlue else TaskGray,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (task.isCompleted) {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    textDecoration = if (task.isCompleted) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                )
                
                if (!task.description.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        maxLines = 2,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
                
                if (task.dueDate != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Due: ${formatDate(task.dueDate)}",
                        fontSize = 12.sp,
                        color = if (isOverdue(task.dueDate)) {
                            TaskBlue
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        }
                    )
                }
                
                if (task.category != "Other") {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.category,
                        fontSize = 12.sp,
                        color = TaskBlue,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(Date(timestamp))
}

private fun isOverdue(timestamp: Long): Boolean {
    return timestamp < System.currentTimeMillis()
}

@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    QuickTasksTheme {
        val sampleTasks = listOf(
            Task(
                id = 1,
                title = "Complete Lab Assignment",
                description = "Finish the Android development lab",
                category = "School",
                dueDate = System.currentTimeMillis() + 86400000, // Tomorrow
                isCompleted = false
            ),
            Task(
                id = 2,
                title = "Buy groceries",
                description = "Milk, bread, eggs",
                category = "Personal",
                isCompleted = true
            ),
            Task(
                id = 3,
                title = "Team meeting",
                description = "Discuss project progress",
                category = "Work",
                dueDate = System.currentTimeMillis() - 86400000, // Yesterday (overdue)
                isCompleted = false
            )
        )
        
        TaskListScreen(
            tasks = sampleTasks,
            onTaskClick = {},
            onTaskToggleComplete = {},
            onAddTaskClick = {}
        )
    }
}
