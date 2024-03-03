package todolist.kmm.presentation.view.features.tasks

import note.data.local.Task
import todolist.kmm.presentation.model.ResourceUIState
import todolist.kmm.presentation.intent.UIEffect
import todolist.kmm.presentation.intent.UIEvent
import todolist.kmm.presentation.intent.UIState

interface TasksContrant {
    sealed interface Event : UIEvent {
        object OnTryCheckAgainClick : Event
        object OnFavoritesClick : Event
        data class OnTaskClick(val idTask: Int) : Event

        object OnAddTaskClick : Event
    }

    data class State(
        val tasks: ResourceUIState<List<Task>>
    ) : UIState

    sealed interface Effect : UIEffect {
        data class NavigateToDetailTask(val idTask: Int) : Effect
        object NavigateToFavorites : Effect

        object NavigatedToInsert : Effect
    }
}