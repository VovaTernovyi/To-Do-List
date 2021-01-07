package com.ternovyi.to_do_list.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ternovyi.to_do_list.extension.LiveResource
import com.ternovyi.to_do_list.extension.onError
import com.ternovyi.to_do_list.model.database.TaskDao
import com.ternovyi.to_do_list.model.entity.Resource
import com.ternovyi.to_do_list.model.entity.Task
import com.ternovyi.to_do_list.model.network.contract.TaskContract

interface TaskRepository {
    fun getAllTasks(): LiveData<List<Task>>
    fun downloadAndSaveTasks(userId: Int): LiveResource<Unit>
    suspend fun addTask(task: Task)
    suspend fun checkTask(taskId: Long, isChecked: Boolean)
}

class TaskRepositoryImplementation(
    private val contract: TaskContract,
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): LiveData<List<Task>> {
        return taskDao.loadTasksLiveData()
    }

    override fun downloadAndSaveTasks(userId: Int): LiveResource<Unit> = liveData {
        runCatching {
            emit(Resource.loading())
            val response = contract.getTasks(userId)
            taskDao.insertTasks(response)
        }.onSuccess {
            emit(Resource.success(it))
        }.onError {
            emit(Resource.error(it, Unit))
        }
    }

    override suspend fun addTask(task: Task) {
        kotlin.runCatching {
            taskDao.insertTask(task)
        }
    }

    override suspend fun checkTask(taskId: Long, isChecked: Boolean) {
        taskDao.updateIsCompletedTask(taskId, isChecked)
    }

}