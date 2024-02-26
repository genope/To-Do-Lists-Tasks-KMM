package todolist.kmm.presentation.ui.features.task_details

import note.data.local.Task
import todolist.kmm.presentation.model.ResourceUIState
import todolist.kmm.presentation.mvi.UIEffect
import todolist.kmm.presentation.mvi.UIEvent
import todolist.kmm.presentation.mvi.UIState

interface TaskDetailContract {
    sealed interface Event : UIEvent {
        object OnFavoriteClick : Event
        object OnTryCheckAgainClick : Event
        object OnBackPressed : Event
    }

    data class State(
        val task: ResourceUIState<Task>,
        val isFavorite: ResourceUIState<Boolean>,
    ) : UIState

    sealed interface Effect : UIEffect {
        object TaskAdded : Effect
        object TaskRemoved : Effect
        object BackNavigation : Effect
    }
}