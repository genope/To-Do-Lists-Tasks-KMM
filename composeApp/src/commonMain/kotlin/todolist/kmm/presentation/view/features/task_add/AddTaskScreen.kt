package todolist.kmm.presentation.view.features.task_add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.TextField
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.collectLatest
import note.data.local.Task
import todolist.kmm.domain.models.Complexity
import todolist.kmm.domain.models.Status
import todolist.kmm.presentation.model.ResourceUIState
import todolist.kmm.presentation.view.common.ActionBarIcon
import todolist.kmm.presentation.view.common.ArrowBackIcon
import todolist.kmm.presentation.view.common.state.ManagementResourceUiState
import todolist.kmm.presentation.view.features.task_details.TaskDetailsScreen

class AddTaskScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val addTaskViewModel = getScreenModel<AddTaskViewModel>()
        val state by addTaskViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow


        LaunchedEffect(key1 = Unit) {
            addTaskViewModel.effect.collectLatest { effect ->
                when (effect) {
                    AddTaskContract.Effect.TaskAdded ->
                        scaffoldState.snackbarHostState.showSnackbar("Task added")
                    AddTaskContract.Effect.BackNavigation -> navigator.pop()


                }
            }
        }

        Scaffold(
            topBar = {
                ActionAppBar(
                    onBackPressed = { addTaskViewModel.setEvent(AddTaskContract.Event.OnBackPressed) }
                )
            }
        ) { task ->
            AddTaskComponents { addTaskViewModel.setEvent(AddTaskContract.Event.OnTaskAddClick(it)) }
        }
    }

    @Composable
    fun AddTaskComponents(onActionAddTask: (Task) -> Unit) {
        var name_task by remember {
            mutableStateOf("")
        }
        var description by remember {
            mutableStateOf("")
        }
        var assigned_to by remember {
            mutableStateOf("")
        }
        var assigned_from by remember {
            mutableStateOf("")
        }
        var status by remember {
            mutableStateOf(0)
        }
        var complexity by remember {
            mutableStateOf(0)
        }

        Card(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxSize()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            elevation = 5.dp,
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth()
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.2f)
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Add Task",
                        fontSize = 45.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                CustomTextInputComponent(
                    name_task,
                    textLabel = "Task Name",
                    ImeAction.Next,
                    Icons.Default.Info,
                ) { name_task = it }
                Spacer(modifier = Modifier.padding(5.dp))
                CustomTextInputComponent(
                    description,
                    textLabel = "Task Description",
                    ImeAction.Next,
                    Icons.Default.MailOutline,
                ) { description = it }
                Spacer(modifier = Modifier.padding(5.dp))
                CustomTextInputComponent(
                    assigned_to,
                    textLabel = "Assigned To",
                    ImeAction.Next,
                    Icons.Default.Person,
                ) { assigned_to = it }
                Spacer(modifier = Modifier.padding(5.dp))
                CustomTextInputComponent(
                    assigned_from,
                    textLabel = "Assigned From",
                    ImeAction.Next,
                    Icons.Filled.Person,
                ) { assigned_from = it }
                Spacer(modifier = Modifier.padding(5.dp))
                CustomTextInputComponent(
                    status.toString(),
                    textLabel = "Task Status",
                    ImeAction.Next,
                    Icons.Default.CheckCircle,
                ) { status = it.toInt() }
                Spacer(modifier = Modifier.padding(5.dp))
                CustomTextInputComponent(
                    complexity.toString(),
                    textLabel = "Task Complexity",
                    ImeAction.Done,
                    Icons.Filled.Menu,
                ) { complexity = it.toInt() }
                Spacer(modifier = Modifier.padding(7.dp))
                ElevatedButton(onClick ={
                    val task = Task(
                        id=0,
                        name_task,
                        description,
                        assigned_to,
                        assigned_from,
                        Status.DONE,
                        Complexity.HARD
                    )
                    onActionAddTask(task)
                } ) {
                    Text("Add Task")
                }
            }


        }
    }

    @Composable
    fun ActionAppBar(
        onBackPressed: () -> Unit,
    ) {
        TopAppBar(
            title = {},
            navigationIcon = { ArrowBackIcon(onBackPressed) },
            actions = {}

        )
    }
}

@Composable
fun CustomTextInputComponent(
    textValue: String,
    textLabel: String,
    imeAction: ImeAction,
    icons: ImageVector,
    onTextChanged: (String) -> Unit
) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = textValue))
    }
    OutlinedTextField(
        label = {
            Text(textLabel)
        },
        value = textFieldValue,
        onValueChange = { newValue ->
            textFieldValue = newValue
            onTextChanged(newValue.text)
        },
        leadingIcon = {
            Icon(
                imageVector = icons,
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = imeAction)
    )
}