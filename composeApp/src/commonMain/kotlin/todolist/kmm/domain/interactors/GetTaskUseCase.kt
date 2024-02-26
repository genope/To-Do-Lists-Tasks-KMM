package todolist.kmm.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import note.data.local.Task
import todolist.kmm.domain.IRepository
import todolist.kmm.domain.interactors.type.BaseUseCase

class GetTaskUseCase(

    private val repository: IRepository,
    dispatcher: CoroutineDispatcher
): BaseUseCase<Int, Task>(dispatcher) {
    override suspend fun block(param: Int): Task = repository.getTask(param)
}