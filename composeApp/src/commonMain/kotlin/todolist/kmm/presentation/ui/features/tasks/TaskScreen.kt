package todolist.kmm.presentation.ui.features.tasks


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import todolist.kmm.presentation.ui.common.ActionBarIcon
import todolist.kmm.presentation.ui.common.state.ManagementResourceUiState
import todolist.kmm.presentation.ui.features.task_details.TaskDetailsScreen
import todolist.kmm.presentation.ui.features.tasks_favorites.TasksFavoritesScreen


class TaskScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val taskViewModel= getScreenModel<TasksViewModel>()
        val state by taskViewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            taskViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is TasksContrant.Effect.NavigateToDetailTask ->
                        navigator.push(TaskDetailsScreen(effect.idTask))
                        TasksContrant.Effect.NavigateToFavorites ->
                        navigator.push(TasksFavoritesScreen())
                }
            }
        }

        Scaffold(
            topBar = {
                ActionAppBar { taskViewModel.setEvent(TasksContrant.Event.OnFavoritesClick) }
            },
        ) { padding ->
            ManagementResourceUiState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                resourceUiState = state.tasks,
                successView = {tasks ->
                    TasksList(
                        tasks = tasks,
                        onTaskClick ={idTask ->
                            taskViewModel.setEvent(
                                TasksContrant.Event.OnTaskClick(
                                    idTask
                                )
                            )

                        }
                    )
                },
                onTryAgain = { taskViewModel.setEvent(TasksContrant.Event.OnTryCheckAgainClick) },
                onCheckAgain = { taskViewModel.setEvent(TasksContrant.Event.OnTryCheckAgainClick) },
            )
        }
    }
    @Composable
    fun ActionAppBar(
        onClickFavorite: () -> Unit,
    ) {
        TopAppBar(
            title = { Text(text = "Todo list KMM") },
            actions = {
                ActionBarIcon(
                    onClick = onClickFavorite,
                    icon = Icons.Filled.Favorite
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
           fun TaskItem(
               name: String,
               description: String,
               assigned_from: String,
               assigned_to: String,
               complexity: Int,
               status: Int,
               id: Int
           ) {
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
                                   ColorByStatus(status)
                                   Text(
                                       modifier = Modifier.padding(2.dp),
                                       text = name,
                                       maxLines = 3,
                                       fontWeight = FontWeight.Bold,
                                       style = MaterialTheme.typography.body1,
                                       color = Color.Black,
                                   )
                                   Column(
                                       modifier = Modifier.fillMaxWidth(),
                                       horizontalAlignment = Alignment.End
                                   ) {
                                       ColorByComplexity(complexity)
                                   }
                                   ColorByComplexity(complexity)
                               }
                               Spacer(modifier = Modifier.height(3.dp))
                               Text(
                                   modifier = Modifier.padding(2.dp)
                                       .padding(start = 20.dp),
                                   text = description,
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
                                           text = "assigned from :${assigned_from} ",
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
                                           text = "assigned To :${assigned_to} ",
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