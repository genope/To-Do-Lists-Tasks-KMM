package todolist.kmm.data_remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskModel(
    @SerialName("items")
    var list: List<Item>
)

@Serializable
data class Item(
    val name_task: String,
    val description: String,
    val assigned_from: String,
    val assigned_to: String,
    val complexity: Int,
    val status: Int,
    val id: Int,
)