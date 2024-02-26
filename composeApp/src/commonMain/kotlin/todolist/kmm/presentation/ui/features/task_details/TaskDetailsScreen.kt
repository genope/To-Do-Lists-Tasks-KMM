package todolist.kmm.presentation.ui.features.task_details


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import daniel.avila.rnm.kmm.presentation.ui.common.TasksList
import kotlinx.coroutines.flow.collectLatest
import note.data.local.Task
import org.koin.core.parameter.parametersOf
import todolist.kmm.data_remote.model.Item
import todolist.kmm.domain.models.Status
import todolist.kmm.presentation.model.ResourceUIState
import todolist.kmm.presentation.ui.common.ActionBarIcon
import todolist.kmm.presentation.ui.common.ArrowBackIcon
import todolist.kmm.presentation.ui.common.state.ManagementResourceUiState
import todolist.kmm.presentation.ui.features.tasks.TasksContrant
import todolist.kmm.presentation.ui.features.tasks.TasksViewModel


class TaskDetailsScreen(
    private val taskId: Int
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val taskDetailViewModel = getScreenModel<TaskDetailViewModel> { parametersOf(taskId) }
        val state by taskDetailViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
          taskDetailViewModel.effect.collectLatest { effect ->
              when(effect){
                  TaskDetailContract.Effect.TaskAdded ->
                      scaffoldState.snackbarHostState.showSnackbar("Task added to favorites")
                  TaskDetailContract.Effect.TaskRemoved ->
                      scaffoldState.snackbarHostState.showSnackbar("Task removed from favorites")
                  TaskDetailContract.Effect.BackNavigation -> navigator.pop()
              }
          }
        }

        Scaffold(
            topBar = {
                ActionAppBar(
                    task = state.task,
                    favorite = state.isFavorite,
                    onActionFavorite = {taskDetailViewModel.setEvent(TaskDetailContract.Event.OnFavoriteClick)},
                    onBackPressed = {taskDetailViewModel.setEvent(TaskDetailContract.Event.OnBackPressed)}
                )
            },
        ) { padding ->
            ManagementResourceUiState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                resourceUiState = state.task,
                successView = { tasks ->
                    TaskItem(tasks)
                },
                onTryAgain = { taskDetailViewModel.setEvent(TaskDetailContract.Event.OnTryCheckAgainClick) },
                onCheckAgain = { taskDetailViewModel.setEvent(TaskDetailContract.Event.OnTryCheckAgainClick) },
            )
        }
    }
    @Composable
    fun TaskItem(task: Task) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
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
                                text = task.status.name, color = when (task.status) {
                                    Status.PENDING -> Color(0xFFF29339)
                                    Status.DONE -> Color.Green
                                    Status.UNKNOWN -> Color.Gray
                                },
                                fontWeight = FontWeight.Bold
                            )
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
                              //  ColorByComplexity(complexity)
                            }
                           // ColorByComplexity(complexity)
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
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(horizontalAlignment = Alignment.Start) {
                                Text(
                                    modifier = Modifier.padding(2.dp)
                                        .padding(start = 20.dp),
                                    text = "assigned from :${task.assigned_from} ",
                                    style = MaterialTheme.typography.body2,
                                    color = Color.Black,
                                )
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    modifier = Modifier.padding(2.dp),
                                    text = "assigned To :${task.assigned_to} ",
                                    style = MaterialTheme.typography.body2,
                                    color = Color.Black,
                                )
                            }

                        }
                    }

                }
            }
        }
    }

    @Composable
    fun ActionAppBar(
        task: ResourceUIState<Task>,
        favorite: ResourceUIState<Boolean>,
        onActionFavorite: () -> Unit,
        onBackPressed: () -> Unit,
    ) {
        TopAppBar(
            title = {
                ManagementResourceUiState(
                    resourceUiState = task,
                    successView = { Text(text = it.name_task) },
                    loadingView = { Text(text = "....") },
                    onCheckAgain = {},
                    onTryAgain = {}
                )
            },
            navigationIcon = { ArrowBackIcon(onBackPressed)},
            actions = {
                ManagementResourceUiState(
                    resourceUiState = favorite,
                    successView = {
                        ActionBarIcon(
                            onClick = onActionFavorite,
                            icon = if (it) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                        )
                    },
                    loadingView = {
                                  ActionBarIcon(
                                      enabled = false,
                                      onClick = {},
                                      icon = Icons.Filled.Favorite
                                  )
                    },
                    onCheckAgain = {},
                    onTryAgain = {}
                )
            }
        )
    }
    /*
        @Composable
        fun TasksCard(
           // onEvent: (getTasksEvent.GetTasks) -> Unit,
           // state:  HomeViewModel.HomeScreenState
            ) {
            val navigator = LocalNavigator.currentOrThrow

            Scaffold(
                topBar = {
                    TopAppBar(
                        contentColor = Color.Black,
                        backgroundColor = Color.White,
                        title = {
                            Text(
                                "List of Todos",
                                maxLines = 1,
                            )
                        },
                        actions = {
                            IconButton(
                                onClick = {

                                }
                            ) {
                                Icon(
                                    Icons.Rounded.Add,
                                    contentDescription = "Add Task"
                                )
                            }
                        }
                    )
                },
            ) {
              LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp),
                    modifier = Modifier.fillMaxSize(),
                    content = {
                    }
                )
            }
        }




               @Composable
               fun ColorByComplexity(complexity: Int) {
                   val color = when (complexity) {
                       1 -> Color.Green
                       2 -> Color.Yellow
                       3 -> Color.Red
                       else -> MaterialTheme.colors.secondary // Handle invalid or unexpected values
                   }

                   Box(
                       modifier = Modifier
                           .size(20.dp) // Consistent size definition
                           .background(color = color)
                   )
               }

               @Composable
               fun ColorByStatus(Status: Int) {
                   val color = when (Status) {
                       0 -> Color.Yellow
                       1 -> Color.Green
                       else -> MaterialTheme.colors.secondary
                   }

                   Canvas(
                       modifier = Modifier
                           .size(20.dp)
                   ) {
                       drawCircle(color = color, radius = 14F) // Adjust radius as desired
                   }
               }

               @OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
               @Composable
               fun DismissBackground(dismissState: DismissState) {
                   val color = when (dismissState.dismissDirection) {
                       DismissDirection.StartToEnd -> Color(0xFFFF1744)
                       DismissDirection.EndToStart -> Color(0xFF1DE9B6)
                       null -> Color.Transparent
                   }
                   val direction = dismissState.dismissDirection

                   Row(
                       modifier = Modifier
                           .fillMaxSize()
                           .background(color)
                           .padding(12.dp, 8.dp),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.SpaceBetween
                   ) {
                       if (direction == DismissDirection.StartToEnd) Icon(
                           Icons.Default.Delete,
                           contentDescription = "delete"
                       )
                       Spacer(modifier = Modifier)
                       if (direction == DismissDirection.EndToStart) Icon(
                           // make sure add baseline_archive_24 resource to drawable folder
                           Icons.Default.Clear,
                           contentDescription = "Archive"
                       )
                   }
               }*/
    /*
        @OptIn(ExperimentalMaterialApi::class)
        @Composable
        fun TaskItemSwipe(
            name: String,
            description: String,
            assigned_from: String,
            assigned_to: String,
            complexity: Int,
            status: Int,
            id: Int
        ) {
            var show by remember { mutableStateOf(true) }
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                        show = false
                        true
                    } else false
                },
            )
            AnimatedVisibility(
                show, exit = fadeOut(spring())
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier,
                    background = {
                        DismissBackground(dismissState)
                    },
                    dismissContent = {
                        TaskItem(name, description, assigned_from, assigned_to, complexity, status, id)
                    }
                )
            }
        }*/


}