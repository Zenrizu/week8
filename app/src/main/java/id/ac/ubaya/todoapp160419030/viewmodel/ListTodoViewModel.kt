package id.ac.ubaya.todoapp160419030.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import id.ac.ubaya.todoapp160419030.model.Todo
import id.ac.ubaya.todoapp160419030.model.TodoDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application)
    :AndroidViewModel(application),CoroutineScope {

    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = Room.databaseBuilder(
                getApplication(),
                TodoDatabase::class.java, "newtododb").build()

            todoLD.value = db.todoDao().selectAllTodo()
        }
    }
    fun clearTask(todo: Todo) {
        launch {
            val db = Room.databaseBuilder(
                getApplication(),
                TodoDatabase::class.java, "newtododb").build()
            db.todoDao().deleteTodo(todo)

            todoLD.value = db.todoDao().selectAllTodo()
        }
    }


}