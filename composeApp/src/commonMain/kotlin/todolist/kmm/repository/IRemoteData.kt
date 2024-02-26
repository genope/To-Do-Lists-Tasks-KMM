package todolist.kmm.repository

import note.data.local.Task

interface IRemoteData {
    suspend fun getTasksFromApi(): List<Task>
    suspend fun getTaskFromApi(id: Int): Task
}