package com.example.a7minutesworkout

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "History_Table")
data class UserEntity(
    @PrimaryKey
    val date: String
)
