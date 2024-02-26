package todolist.kmm.presentation.ui.common.state

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import todolist.kmm.presentation.model.ResourceUIState

@Composable
fun <T> ManagementResourceUiState(
    modifier: Modifier = Modifier,
    resourceUiState: ResourceUIState<T>,
    successView: @Composable (data: T) -> Unit,
    loadingView: @Composable () -> Unit = { Loading() },
    onTryAgain: () -> Unit,
    msgTryAgain: String = "No data to show",
    onCheckAgain: () -> Unit,
    msgCheckAgain: String = "An error has occurred"
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (resourceUiState) {
            is ResourceUIState.Empty -> Empty(modifier = modifier, onCheckAgain = onCheckAgain, msg = msgCheckAgain)
            is ResourceUIState.Error -> Error(modifier = modifier, onTryAgain = onTryAgain, msg = msgTryAgain)
            ResourceUIState.Loading -> loadingView()
            is ResourceUIState.Success -> successView(resourceUiState.data)
            ResourceUIState.Idle -> Unit
        }
    }
}