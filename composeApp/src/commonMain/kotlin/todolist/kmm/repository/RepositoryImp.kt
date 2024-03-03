package todolist.kmm.repository


import todolist.kmm.domain.IRepository
import kotlinx.coroutines.flow.Flow
import note.data.local.Task


class RepositoryImp(
    private val cacheData: ICacheData,
    private val remoteData: IRemoteData,
) : IRepository {

    override suspend fun getTasks(): List<Task> =
        remoteData.getTasksFromApi()

    override suspend fun getTasksFavorites(): Flow<List<Task>> =
        cacheData.getAllTaskFavorites()

    override suspend fun getTask(id: Int): Task =
        remoteData.getTaskFromApi(id)

    override suspend fun addTaskToFavorites(task: Task) =
        cacheData.addTaskToFavorite(task)

    override suspend fun removeTaskFromFavorite(idTask: Int) =
        cacheData.removeTaskFromFavorite(idTask)

    override suspend fun isTaskFavorite(idTask: Int): Boolean =
        cacheData.isTaskFavorite(idTask)

    override suspend fun addTask(task: Task): Boolean =
        remoteData.addTask(task)

}