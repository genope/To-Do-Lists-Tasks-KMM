package note.data.local

import todolist.kmm.domain.models.Complexity
import todolist.kmm.domain.models.Status

data class Task(
    val id: Long,
    val name_task: String,
    val description: String,
    val assigned_from: String,
    val assigned_to: String,
    val status: Status,
    val complexity: Complexity,
)