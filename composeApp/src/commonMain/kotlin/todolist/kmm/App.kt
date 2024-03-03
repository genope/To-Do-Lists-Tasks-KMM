package todolist.kmm

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import todolist.kmm.presentation.view.theme.AppTheme
import todolist.kmm.presentation.view.features.tasks.TaskScreen

@Composable
internal fun App() = AppTheme {
    Navigator(TaskScreen())
}
