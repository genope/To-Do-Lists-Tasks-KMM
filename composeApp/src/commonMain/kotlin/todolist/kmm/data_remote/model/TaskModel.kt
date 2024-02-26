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
    @SerialName("id")
    val id: Int,
    @SerialName("name_task")
    val name_task: String,
    @SerialName("description")
    val description: String,
    @SerialName("complexity")
    val complexity: Int,
    @SerialName("assigned_to")
    val assigned_to: String,
    @SerialName("assigned_from")
    val assigned_from: String,
    @SerialName("status")
    val status: Int,
)