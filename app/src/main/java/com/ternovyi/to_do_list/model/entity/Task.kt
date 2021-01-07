package com.ternovyi.to_do_list.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey
    val id: Long,
    val userId: Int,
    val title: String,
    val isCompleted: Boolean
)