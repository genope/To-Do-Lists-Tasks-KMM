package todolist.kmm.data_remote

import io.github.aakira.napier.Napier
import todolist.kmm.data_remote.model.Item
import todolist.kmm.data_remote.model.TaskModel
import todolist.kmm.data_remote.model.mapper.ApiTaskMapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import note.data.local.Task
import todolist.kmm.data_remote.model.toMyData
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
    override suspend fun addTask(task: Task): Boolean {
        val test = apiTaskMapper.inverseMap(task)
        Napier.d("$test", null    , "Task added successfully")

        // 1. Convert task to a JSON string
        val taskJson = Json.encodeToString(test.toMyData())

        // 2. Perform POST request using HttpClient
        val response:HttpResponse  = httpClient.post("$endPoint/Task/Tasks")
        {
            contentType(ContentType.Application.Json)
            setBody(test.toMyData())
        }

        // 3. Check for successful response (2xx code)
        if (response.status.isSuccess()) {
            Napier.v("HTTP Client", null    , "Task added successfully")
            return true // task added successfully
        } else {
            Napier.e("HTTP Client", null, "Failed to add task: ${response.request.content}")
            return false // task addition failed
        }
    }
}

