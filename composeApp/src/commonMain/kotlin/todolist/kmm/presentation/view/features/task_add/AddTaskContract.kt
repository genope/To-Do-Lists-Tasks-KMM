package todolist.kmm.presentation.view.features.task_add

import note.data.local.Task
import todolist.kmm.presentation.model.ResourceUIState
import todolist.kmm.presentation.intent.UIEffect
import todolist.kmm.presentation.intent.UIEvent
import todolist.kmm.presentation.intent.UIState

interface AddTaskContract {
    sealed interface Event : UIEvent {
        object OnBackPressed : Event
        data class OnTaskAddClick(val task: Task) : Event

    }

    data class State(
        val task: ResourceUIState<Task>,
        val addedTask: ResourceUIState<Boolean>,
    ) : UIState

    sealed interface Effect : UIEffect {
        object TaskAdded : Effect
        object BackNavigation : Effect
    }
}