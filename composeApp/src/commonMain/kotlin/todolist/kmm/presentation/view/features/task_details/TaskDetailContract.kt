package todolist.kmm.presentation.view.features.task_details

import note.data.local.Task
import todolist.kmm.presentation.model.ResourceUIState
import todolist.kmm.presentation.intent.UIEffect
import todolist.kmm.presentation.intent.UIEvent
import todolist.kmm.presentation.intent.UIState

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