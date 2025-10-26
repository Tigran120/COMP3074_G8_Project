package ca.gbc.comp3074.quicktasks.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.gbc.comp3074.quicktasks.R
import ca.gbc.comp3074.quicktasks.model.Task
import ca.gbc.comp3074.quicktasks.ui.theme.QuickTasksTheme
import ca.gbc.comp3074.quicktasks.ui.theme.TaskBlue
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onBackClick: () -> Unit,
    onSaveTask: (String, String?, Long?, String) -> Unit,
    existingTask: Task? = null
) {
    var title by remember { mutableStateOf(existingTask?.title ?: "") }
    var description by remember { mutableStateOf(existingTask?.description ?: "") }
    var selectedDate by remember { mutableStateOf(existingTask?.dueDate) }
    var selectedCategory by remember { mutableStateOf(existingTask?.category ?: "Other") }
    var showDatePicker by remember { mutableStateOf(false) }
    
    val categories = listOf("School", "Work", "Personal", "Other")
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = if (existingTask != null) "Edit Task" else stringResource(R.string.add_task_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.task_title_hint)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.task_description_hint)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
            
            Text(
                text = stringResource(R.string.category_hint),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    FilterChip(
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        selected = selectedCategory == category,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            Text(
                text = stringResource(R.string.due_date_hint),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = if (selectedDate != null) {
                        formatDate(selectedDate!!)
                    } else {
                        "No due date"
                    },
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier.weight(1f)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = { showDatePicker = true }
                ) {
                    Text("Set Date")
                }
                
                if (selectedDate != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(
                        onClick = { selectedDate = null }
                    ) {
                        Text("Clear")
                    }
                }
            }
            
            Button(
                onClick = {
                    if (title.isNotEmpty()) {
                        onSaveTask(title, description.ifEmpty { null }, selectedDate, selectedCategory)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = title.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TaskBlue
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.save_task),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
    
    if (showDatePicker) {
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        
        // Set initial date to existing task's due date or current date
        if (selectedDate != null) {
            calendar.timeInMillis = selectedDate!!
        }
        
        val datePickerDialog = android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)
                selectedDate = selectedCalendar.timeInMillis
                showDatePicker = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        
        // Allow selecting any date (no restrictions)
        datePickerDialog.datePicker.minDate = 0
        
        LaunchedEffect(showDatePicker) {
            if (showDatePicker) {
                datePickerDialog.show()
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(Date(timestamp))
}

@Preview(showBackground = true)
@Composable
fun AddTaskScreenPreview() {
    QuickTasksTheme {
        AddTaskScreen(
            onBackClick = {},
            onSaveTask = { _, _, _, _ -> }
        )
    }
}
