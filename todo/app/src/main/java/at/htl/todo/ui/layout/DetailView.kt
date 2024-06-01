package at.htl.todo.ui.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore
import at.htl.todo.model.Todo
import at.htl.todo.ui.theme.TodoAppTheme

@Composable
fun TodoDetail(model: Model, todoId: Int?, store: ModelStore? = null) {
    val todo = model.todos[todoId!!.toInt() - 1]

    Surface {
        Column {
            Row {
                IconButton(onClick = { store?.apply { model -> model.detailedTodoId = null } }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = todo.id.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(checked = todo.completed, onCheckedChange = {
                    store?.apply {
                            model -> model.todos[todo.id.toInt() - 1].completed = !todo.completed
                    }
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    val todo = Todo()
    todo.id = 1
    todo.title = "First Todo"
    val model = Model()
    model.todos = arrayOf(todo)

    TodoAppTheme {
        TodoDetail(model, 1)
    }
}
