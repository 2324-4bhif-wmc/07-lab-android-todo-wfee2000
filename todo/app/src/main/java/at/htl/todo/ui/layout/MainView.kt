package at.htl.todo.ui.layout

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore
import at.htl.todo.model.Todo
import at.htl.todo.ui.theme.TodoAppTheme
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainView @Inject constructor() {

    @Inject
    lateinit var store: ModelStore

    fun buildContent(activity: ComponentActivity) {
        activity.enableEdgeToEdge()
        activity.setContent {
            val viewModel = store
                .pipe
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAsState(initial = Model())
                .value
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Todos(
                    model = viewModel,
                    modifier = Modifier.padding(all = 32.dp),
                    store = store
                )
            }
        }
    }
}

@Composable
fun Todos(model: Model, modifier: Modifier = Modifier, store: ModelStore? = null) {
    val todos = model.todos
    when (model.detailTodo.todo) {
        null -> {
            LazyColumn(
                modifier = modifier.padding(16.dp)
            ) {
                items(todos.size) { index ->
                    TodoRow(todo  = todos[index], action = {
                        store?.setDetail(todos[index])
                    }
                    )
                    HorizontalDivider()
                }
            }
        }
        else -> {
            if (store != null) {
                TodoDetail(todo = model.detailTodo.todo, store = store )
            }
        }
    }
}

@Composable
fun TodoRow(todo: Todo, action: () -> Unit) {
    Card(colors =
    if (todo.completed)
        CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    else
        CardDefaults.cardColors(MaterialTheme.colorScheme.errorContainer),
        modifier = Modifier
            .height(50.dp)
            .width(500.dp),
        onClick = { action() }) {
        Text(text = todo.title, fontSize = 40.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun TodoViewPreview() {
    val model = Model()
    val todo = Todo()
    todo.id = 1
    todo.title = "First Todo"
    model.todos = arrayOf(todo)

    TodoAppTheme {
        Todos(model)
    }
}

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    val todo = Todo()
    todo.id = 1
    todo.title = "First Todo"

    TodoAppTheme {
        TodoRow(todo = todo, action = {})
    }
}
