package todolist.kmm.presentation.ui.features.tasks_favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import daniel.avila.rnm.kmm.presentation.ui.common.TasksList
import kotlinx.coroutines.flow.collectLatest
import todolist.kmm.presentation.ui.common.ArrowBackIcon
import todolist.kmm.presentation.ui.common.state.ManagementResourceUiState
import todolist.kmm.presentation.ui.features.task_details.TaskDetailContract
import todolist.kmm.presentation.ui.features.task_details.TaskDetailsScreen

class TasksFavoritesScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val tasksFavoritesViewModel = getScreenModel<TasksFavoritesViewModel>()
        val state by tasksFavoritesViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            tasksFavoritesViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is TasksFavoritesContract.Effect.NavigateToDetailTask ->
                        navigator.push(TaskDetailsScreen(effect.idTask))

                    TasksFavoritesContract.Effect.BackNavigation ->
                        navigator.pop()
                }
            }
        }

        Scaffold(
            topBar = {
                ActionBar(onBackPressed = {
                    tasksFavoritesViewModel.setEvent(
                        TasksFavoritesContract.Event.OnBackPressed
                    )
                })
            }
        ) { padding ->
            ManagementResourceUiState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                resourceUiState = state.taskFavorites,
                successView = { favorites ->
                    TasksList(
                        tasks = favorites,
                        onTaskClick = { idTask ->
                            tasksFavoritesViewModel.setEvent(
                                TasksFavoritesContract.Event.OnTaskClick(
                                    idTask
                                )
                            )
                        }
                    )
                },
                onTryAgain = { tasksFavoritesViewModel.setEvent(TasksFavoritesContract.Event.OnTryCheckAgainClick) },
                onCheckAgain = { tasksFavoritesViewModel.setEvent(TasksFavoritesContract.Event.OnTryCheckAgainClick) },
                msgCheckAgain = "Favorites list is empty"
            )
        }
    }

}

@Composable
fun ActionBar(
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "Tasks Favorites") },
        navigationIcon = { ArrowBackIcon(onBackPressed) }
    )
}