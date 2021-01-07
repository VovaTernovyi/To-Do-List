package com.ternovyi.to_do_list.di.modules

import com.ternovyi.to_do_list.viewModel.ToDoListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val KoinArchitectureComponentViewModels = module {
    viewModel { ToDoListViewModel(repository = get()) }
}