package com.habibi.core.data.source.local

import com.habibi.core.data.source.local.entity.UsersEntity
import com.habibi.core.data.source.local.room.FindDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val findDao: FindDao){

    fun getSearchUsers(): Flow<List<UsersEntity>> = findDao.getSearchUsers()

    suspend fun insertSearchUsers(list: List<UsersEntity>) = findDao.insertSearchUsers(list)

    suspend fun deleteSearchUsers() = findDao.deleteSearchUsers()

}