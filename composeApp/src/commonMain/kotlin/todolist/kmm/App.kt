package todolist.kmm

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import todolist.kmm.presentation.ui.theme.AppTheme
import todolist.kmm.presentation.ui.features.tasks.TaskScreen

@Composable
internal fun App() = AppTheme {
    Navigator(TaskScreen())
}
