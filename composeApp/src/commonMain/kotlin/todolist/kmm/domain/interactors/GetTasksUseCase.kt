package todolist.kmm.domain.interactors

import todolist.kmm.domain.IRepository
import todolist.kmm.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import note.data.local.Task


class GetTasksUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, List<Task>>(dispatcher) {
    override suspend fun block(param: Unit): List<Task> = repository.getTasks()
}