package todolist.kmm.presentation.ui.features.tasks_favorites

import note.data.local.Task
import todolist.kmm.presentation.model.ResourceUIState
import todolist.kmm.presentation.mvi.UIEffect
import todolist.kmm.presentation.mvi.UIEvent
import todolist.kmm.presentation.mvi.UIState

interface TasksFavoritesContract {
    sealed interface Event: UIEvent{
        object OnBackPressed: Event
        object OnTryCheckAgainClick : Event
        data class OnTaskClick(val idTask: Int): Event
    }

    data class State(
        val taskFavorites: ResourceUIState<List<Task>>
    ): UIState

    sealed interface Effect :UIEffect{
        data class NavigateToDetailTask(val idTask: Int) : Effect
        object BackNavigation:Effect
    }
}