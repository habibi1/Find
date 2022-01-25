package com.habibi.core.data.source

import com.habibi.core.data.source.local.entity.UsersEntity
import com.habibi.core.data.source.remote.response.DetailUserResponse
import com.habibi.core.data.source.remote.response.UserRepositoryResponseItem
import kotlinx.coroutines.flow.Flow

interface IUsersDataSource {
    fun getSearchUsers(query: String, queryIsSame: Boolean): Flow<Resource<List<UsersEntity>>>
    fun getDetailUser(login: String): Flow<Resource<DetailUserResponse>>
    fun getUserRepository(login: String): Flow<Resource<List<UserRepositoryResponseItem?>>>
    fun getKeywordSearch(): Flow<String>
    suspend fun saveKeywordSearch(keyword: String)
}