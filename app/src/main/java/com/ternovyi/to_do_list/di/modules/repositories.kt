package com.ternovyi.to_do_list.di.modules

import com.ternovyi.to_do_list.model.repository.TaskRepository
import com.ternovyi.to_do_list.model.repository.TaskRepositoryImplementation
import org.koin.dsl.module

val KoinRepositoriesModule = module {
    single<TaskRepository> { TaskRepositoryImplementation(contract = get(), taskDao = get()) }
}