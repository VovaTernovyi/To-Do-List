package com.ternovyi.to_do_list.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ternovyi.to_do_list.model.entity.Task

@Database(
    entities = [Task::class],
    version = ToDoListDatabase.DATABASE_VERSION,
    exportSchema = ToDoListDatabase.EXPORT_SCHEMA
)
abstract class ToDoListDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_VERSION = 1
        const val EXPORT_SCHEMA = false
        const val DATABASE_NAME = "to_do_list_database"
    }
}