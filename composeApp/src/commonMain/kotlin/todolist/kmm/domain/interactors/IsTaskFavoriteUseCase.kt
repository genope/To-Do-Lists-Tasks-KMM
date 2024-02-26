package todolist.kmm.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import todolist.kmm.domain.IRepository
import todolist.kmm.domain.interactors.type.BaseUseCase


class IsTaskFavoriteUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher
): BaseUseCase<Int,Boolean>(dispatcher) {
    override suspend fun block(param: Int): Boolean = repository.isTaskFavorite(param)

}