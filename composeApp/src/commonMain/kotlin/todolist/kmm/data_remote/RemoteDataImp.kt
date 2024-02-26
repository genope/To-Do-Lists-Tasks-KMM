package todolist.kmm.data_remote

import todolist.kmm.data_remote.model.Item
import todolist.kmm.data_remote.model.TaskModel
import todolist.kmm.data_remote.model.mapper.ApiTaskMapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import note.data.local.Task
import todolist.kmm.repository.IRemoteData

class RemoteDataImp(
    private val endPoint: String,
    private val httpClient: HttpClient,
    private val apiTaskMapper: ApiTaskMapper,
) : IRemoteData {
    override suspend fun getTasksFromApi(): List<Task> =
        apiTaskMapper.map(
            (httpClient.get("$endPoint/Task/Tasks").body<TaskModel>()).list
        )
    override suspend fun getTaskFromApi(id: Int): Task =
        apiTaskMapper.map(
            httpClient.get("$endPoint/Task/ListerTasks/$id").body<Item>()
        )


}