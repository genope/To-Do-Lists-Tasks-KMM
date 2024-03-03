package todolist.kmm.data_remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import note.data.local.Task
import todolist.kmm.domain.models.Complexity
import todolist.kmm.domain.models.Status

@Serializable
data class TaskModel(
    @SerialName("items")
    var list: List<Item>
)
@Serializable
data class Item(
    @SerialName("id")
    val id: Int,
    @SerialName("name_task")
    val name_task: String,
    @SerialName("description")
    val description: String,
    @SerialName("assigned_to")
    val assigned_to: String,
    @SerialName("assigned_from")
    val assigned_from: String,
    @SerialName("status")
    val status: Int,
    @SerialName("complexity")
    val complexity: Int,
)
@Serializable
data class TaskWithoutId(
    @Transient
    val id: Int = 0,
    val name_task: String,
    val description: String,
    val assigned_from: String,
    val assigned_to: String,
    val status: Int,
    val complexity: Int
)

fun Item.toMyData(): TaskWithoutId {

    return TaskWithoutId(
        name_task = this.name_task,
        description = this.description,
        assigned_from = this.assigned_from,
        assigned_to = this.assigned_to,
        status = this.status,
        complexity = this.complexity,
    )
}