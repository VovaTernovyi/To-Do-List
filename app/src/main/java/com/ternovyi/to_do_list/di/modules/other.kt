package com.ternovyi.to_do_list.di.modules

import com.ternovyi.to_do_list.view.adapter.TaskAdapter
import org.koin.dsl.module

val KoinOtherModule = module {
    factory { TaskAdapter() }
}