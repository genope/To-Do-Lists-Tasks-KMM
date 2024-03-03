package todolist.kmm.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import note.data.local.Task
import todolist.kmm.domain.IRepository
import todolist.kmm.domain.interactors.type.BaseUseCase

class AddTaskUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Task,Boolean >(dispatcher) {
    override suspend fun block(param: Task): Boolean = repository.addTask(param)
}
