package com.ternovyi.to_do_list.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ternovyi.to_do_list.model.entity.Task

@Dao
abstract class TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertTasks(tasks: List<Task>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTask(task: Task)

    @Query("SELECT * FROM Task")
    abstract fun loadTasksLiveData(): LiveData<List<Task>>

    @Query("UPDATE Task SET isCompleted = :isCompleted WHERE id = :id")
    abstract suspend fun updateIsCompletedTask(id: Long, isCompleted: Boolean)

}