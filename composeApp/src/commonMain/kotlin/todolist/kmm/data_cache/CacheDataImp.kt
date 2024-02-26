package todolist.kmm.data_cache

import app.cash.sqldelight.coroutines.asFlow
import todolist.kmm.data_cache.sqldelight.SharedDatabase
import todolist.kmm.domain.models.Complexity
import todolist.kmm.domain.models.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import note.data.local.Task
import todolist.kmm.repository.ICacheData

class CacheDataImp(
    private val sharedDatabase: SharedDatabase,
) : ICacheData {

    override suspend fun addTaskToFavorite(task: Task) {
        sharedDatabase {
            it.appDatabaseQueries.insertTaskFavorite(
                task.id,
                task.name_task,
                task.description,
                task.assigned_to,
                task.assigned_from,
                task.status,
                task.complexity,
            )
        }
    }

    override suspend fun removeTaskFromFavorite(idTask: Int) {
        sharedDatabase {
            it.appDatabaseQueries.removeTaskFavorite(idTask.toLong())
        }
    }

    override suspend fun getAllTaskFavorites(): Flow<List<Task>> =
        sharedDatabase { appDatabase ->
            appDatabase.appDatabaseQueries.selectAllTaskFavorite(::mapFavorite).asFlow()
                .map { it.executeAsList() }
        }

    override suspend fun isTaskFavorite(idTask: Int): Boolean =
        sharedDatabase {
            it.appDatabaseQueries.selectTaskFavoriteById(idTask.toLong()).executeAsOne()
        }

    private fun mapFavorite(
        id: Long,
        name_task: String,
        description: String,
        assigned_to: String,
        assigned_from: String,
        status: Status,
        complexity: Complexity,
    ): Task = Task(id, name_task, description,assigned_to, assigned_from, status,  complexity)
}