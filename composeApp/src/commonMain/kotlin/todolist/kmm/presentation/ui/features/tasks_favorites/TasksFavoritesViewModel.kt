package todolist.kmm.presentation.ui.features.tasks_favorites

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import todolist.kmm.domain.interactors.GetTasksFavoritesUseCase
import todolist.kmm.presentation.model.ResourceUIState
import todolist.kmm.presentation.mvi.BaseViewModel

class TasksFavoritesViewModel(
    private val getTasksFavoritesUseCase: GetTasksFavoritesUseCase,
) : BaseViewModel<TasksFavoritesContract.Event,TasksFavoritesContract.State,TasksFavoritesContract.Effect>() {

    init {
        getTasksFavorites()
    }

    override fun createInitialState(): TasksFavoritesContract.State =
        TasksFavoritesContract.State(
            taskFavorites = ResourceUIState.Idle
        )

    override fun handleEvent(event: TasksFavoritesContract.Event) {
        when(event){
            TasksFavoritesContract.Event.OnTryCheckAgainClick -> getTasksFavorites()
            is TasksFavoritesContract.Event.OnTaskClick ->
                setEffect { TasksFavoritesContract.Effect.NavigateToDetailTask(event.idTask) }
            TasksFavoritesContract.Event.OnBackPressed ->
                setEffect { TasksFavoritesContract.Effect.BackNavigation }
        }
    }

    private fun getTasksFavorites(){
        setState { copy(taskFavorites= ResourceUIState.Loading) }

        screenModelScope.launch {
            getTasksFavoritesUseCase(Unit).collect{
                it.onSuccess {
                    setState {
                        copy(
                            taskFavorites =
                                if(it.isEmpty())
                            ResourceUIState.Empty
                            else
                            ResourceUIState.Success(it)
                        )
                    }
                }.onFailure { setState { copy(taskFavorites=ResourceUIState.Error()) } }
            }
        }
    }


}