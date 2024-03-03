package todolist.kmm.presentation.view.features.tasks_favorites

import note.data.local.Task
import todolist.kmm.presentation.model.ResourceUIState
import todolist.kmm.presentation.intent.UIEffect
import todolist.kmm.presentation.intent.UIEvent
import todolist.kmm.presentation.intent.UIState

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