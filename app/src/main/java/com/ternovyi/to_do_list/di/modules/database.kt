package com.ternovyi.to_do_list.di.modules

import androidx.room.Room
import com.ternovyi.to_do_list.model.database.ToDoListDatabase
import org.koin.dsl.module

val KoinDatabaseModule = module {

    single {
        Room.databaseBuilder(
            get(),
            ToDoListDatabase::class.java,
            ToDoListDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    factory { get<ToDoListDatabase>().taskDao() }
}