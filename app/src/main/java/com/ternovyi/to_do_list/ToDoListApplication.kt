package com.ternovyi.to_do_list

import android.app.Application
import com.ternovyi.to_do_list.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToDoListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDependencyInjector()
    }

    private fun initDependencyInjector() {
        startKoin {
            androidContext(this@ToDoListApplication)
            modules(
                KoinApiModule,
                KoinDatabaseModule,
                KoinRepositoriesModule,
                KoinArchitectureComponentViewModels,
                KoinOtherModule
            )
        }
    }

    companion object {
        lateinit var instance: ToDoListApplication
            private set
    }
}