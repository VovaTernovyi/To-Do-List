package com.ternovyi.to_do_list.model.network.contract

import com.ternovyi.to_do_list.model.entity.Task
import com.ternovyi.to_do_list.model.network.ApiRest
import retrofit2.http.GET
import retrofit2.http.Query

interface TaskContract {

    @GET(ApiRest.TASK_LIST)
    suspend fun getTasks(
        @Query("userId") userId: Int
    ): List<Task>

}