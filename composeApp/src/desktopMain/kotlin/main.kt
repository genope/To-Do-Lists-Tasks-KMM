import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import todolist.kmm.App
import todolist.kmm.di.initKoin

fun main() = application {
    initKoin {}
    Window(
        title = "Todo List KMM",
        state = rememberWindowState(width = 400.dp, height = 800.dp),
        onCloseRequest = ::exitApplication,
    ) { App() }
}