package todolist.kmm.domain

import kotlinx.coroutines.flow.Flow
import note.data.local.Task

interface IRepository {
    suspend fun getTasks(): List<Task>
    suspend fun getTasksFavorites(): Flow<List<Task>>
    suspend fun getTask(id: Int): Task
    suspend fun addTaskToFavorites(task: Task)
    suspend fun removeTaskFromFavorite(idTask: Int)
    suspend fun isTaskFavorite(idTask: Int): Boolean
}