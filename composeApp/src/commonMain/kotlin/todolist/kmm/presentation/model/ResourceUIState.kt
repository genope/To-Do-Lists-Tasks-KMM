package todolist.kmm.presentation.model

sealed interface ResourceUIState<out T> {
    data class Success<T>(val data: T): ResourceUIState<T>
    data class Error(val message: String?= null): ResourceUIState<Nothing>
    object  Loading : ResourceUIState<Nothing>
    object  Empty : ResourceUIState<Nothing>
    object  Idle : ResourceUIState<Nothing>
}