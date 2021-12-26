package com.example.a7minutesworkout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(userEntity: UserEntity)
    @Query("SELECT * FROM `History_Table`")
    fun fetchAllDate(): Flow<List<UserEntity>>
}