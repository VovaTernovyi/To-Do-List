package com.ternovyi.to_do_list.viewModel

import android.util.Log
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ternovyi.to_do_list.extension.onError
import com.ternovyi.to_do_list.model.entity.SingleLiveEvent
import com.ternovyi.to_do_list.model.entity.Task
import com.ternovyi.to_do_list.model.repository.TaskRepository
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class ToDoListViewModel(
    private val repository: TaskRepository
) : ViewModel(), KoinComponent {

    private val downloadTasksLiveData = SingleLiveEvent<Unit>()
    private val refreshTasksLiveData = SingleLiveEvent<Unit>()

    val downloadAndSaveTasksLiveData =
        Transformations.switchMap(downloadTasksLiveData) {
            repository.downloadAndSaveTasks(userId = 1)//We should pass correct userId if we will have authorized user.
        }

    val tasksLiveData = Transformations.switchMap(refreshTasksLiveData) {
        repository.getAllTasks()
    }

    private fun addTask(task: Task) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.addTask(task)
            }.onSuccess {
                Log.i("Add new task: ", "Success")
            }.onError {
                Log.e("Add new task: ", "Error")
            }
        }
    }

    fun checkTask(taskId: Long, isChecked: Boolean) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.checkTask(taskId, isChecked)
            }.onSuccess {
                Log.i("Update task: ", "Success")
            }.onError {
                Log.e("Update task: ", "Error")
            }
        }
    }

    fun downloadTasks() {
        downloadTasksLiveData.call()
    }

    fun refreshTasks() {
        refreshTasksLiveData.call()
    }

    fun onClickAddNewTask(text: String) {
        if (isValidTask(text)) {
            addTask(
                Task(
                    id = getUniqueId(),
                    userId = 1,
                    title = text,
                    isCompleted = false
                )
            )//We should pass correct userId if we will have authorized user.
        }
    }

    private fun isValidTask(text: String): Boolean {
        return text.isNotBlank()
    }

    private fun getUniqueId(): Long {
        return System.currentTimeMillis();
    }

}