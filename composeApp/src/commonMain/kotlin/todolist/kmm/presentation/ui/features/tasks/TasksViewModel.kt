package todolist.kmm.presentation.ui.features.tasks

import todolist.kmm.domain.interactors.GetTasksUseCase
import todolist.kmm.presentation.model.ResourceUIState
import todolist.kmm.presentation.mvi.BaseViewModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch

class TasksViewModel(
    private val getTasksUseCase: GetTasksUseCase,
) : BaseViewModel<TasksContrant.Event, TasksContrant.State, TasksContrant.Effect>() {
    init {
        getTasks()
    }

    override fun createInitialState(): TasksContrant.State =
        TasksContrant.State(tasks = ResourceUIState.Idle)

    override fun handleEvent(event: TasksContrant.Event) {
        when (event) {
            TasksContrant.Event.OnTryCheckAgainClick -> getTasks()
            is TasksContrant.Event.OnTaskClick -> setEffect {
                TasksContrant.Effect.NavigateToDetailTask(
                    event.idTask
                )
            }

            TasksContrant.Event.OnFavoritesClick -> setEffect { TasksContrant.Effect.NavigateToFavorites }

        }
    }
    private fun getTasks() {
        setState { copy(tasks = ResourceUIState.Loading) }
        screenModelScope.launch {
            getTasksUseCase(Unit)
                .onSuccess {
                    setState {
                        copy(
                            tasks = if (it.isEmpty())
                                ResourceUIState.Empty
                            else
                                ResourceUIState.Success(it)
                        )
                    }
                }
                .onFailure { setState { copy(tasks = ResourceUIState.Error()) } }
        }
    }
}