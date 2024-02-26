package todolist.kmm.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import note.data.local.Task
import todolist.kmm.domain.IRepository
import todolist.kmm.domain.interactors.type.BaseUseCaseFlow

class GetTasksFavoritesUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
): BaseUseCaseFlow<Unit, List<Task>>(dispatcher) {
    override suspend fun build(param: Unit): Flow<List<Task>> = repository.getTasksFavorites()

}