package daniel.avila.rnm.kmm.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import note.data.local.Task
import todolist.kmm.presentation.view.common.TaskItem

@Composable
fun TasksList(
    tasks: List<Task>,
    onTaskClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        items(tasks) { task ->
            TaskItem(
                task = task,
                onClick = { onTaskClick(task.id!!.toInt()) }
            )
        }
    }
}