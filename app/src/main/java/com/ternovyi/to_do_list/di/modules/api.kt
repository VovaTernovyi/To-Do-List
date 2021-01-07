package com.ternovyi.to_do_list.di.modules

import com.google.gson.GsonBuilder
import com.ternovyi.to_do_list.BuildConfig
import com.ternovyi.to_do_list.model.network.contract.TaskContract
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val KoinApiModule = module {
    single(definition = { getOkHttpClient() })
    single { getRetrofit(client = get()) }

    factory { provideTasks(retrofit = get()) }
}

fun provideTasks(retrofit: Retrofit): TaskContract = retrofit.create(TaskContract::class.java)

fun getOkHttpClient(): OkHttpClient =
    with(OkHttpClient.Builder()) {
        followRedirects(false)
        connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        readTimeout(10, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        writeTimeout(10, java.util.concurrent.TimeUnit.SECONDS) // default: 10 seconds
        return build()
    }

fun getRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(client)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build()