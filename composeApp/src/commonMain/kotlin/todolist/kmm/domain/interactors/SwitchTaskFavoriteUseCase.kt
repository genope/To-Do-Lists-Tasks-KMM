package todolist.kmm.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import todolist.kmm.domain.IRepository
import todolist.kmm.domain.interactors.type.BaseUseCase

class SwitchTaskFavoriteUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Int, Boolean>(dispatcher) {
    override suspend fun block(param: Int): Boolean {
        if (repository.isTaskFavorite(param)) {
            repository.removeTaskFromFavorite(param)
        } else {
            repository.addTaskToFavorites(repository.getTask(param))
        }
        return repository.isTaskFavorite(param)
    }
}