package todolist.kmm.presentation.view.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import note.data.local.Task

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = task.name_task,
                            maxLines = 3,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.body1,
                            color = Color.Black,
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            //ColorByComplexity(complexity)
                        }
                        //ColorByComplexity(complexity)
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        modifier = Modifier.padding(2.dp)
                            .padding(start = 20.dp),
                        text = task.description,
                        fontWeight = FontWeight.Normal,
                        maxLines = 4,
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray
                    )
                }

            }
        }
    }
}



