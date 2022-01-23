package com.habibi.core.data.source.local.room

import androidx.room.*
import com.habibi.core.data.source.local.entity.UsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FindDao {
    @Query("SELECT * from users_entities")
    fun getSearchUsers(): Flow<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchUsers(list: List<UsersEntity>)

    @Query("DELETE FROM users_entities")
    suspend fun deleteSearchUsers()
}