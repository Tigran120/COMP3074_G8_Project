package ca.gbc.comp3074.quicktasks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val dueDate: Long? = null,
    val category: String = "Other",
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
