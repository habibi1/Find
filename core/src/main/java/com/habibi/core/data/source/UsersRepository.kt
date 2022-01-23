package com.habibi.core.data.source

import com.habibi.core.data.source.datastore.FindPreference
import com.habibi.core.data.source.local.LocalDataSource
import com.habibi.core.data.source.local.entity.UsersEntity
import com.habibi.core.data.source.remote.RemoteDataSource
import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.data.source.remote.response.DetailUserResponse
import com.habibi.core.data.source.remote.response.UsersItem
import com.habibi.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

class UsersRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val findPreference: FindPreference
): IUsersDataSource {
    override fun getSearchUsers(query: String, queryIsSame: Boolean): Flow<Resource<List<UsersEntity>>> =
        object : NetworkBoundResource<List<UsersEntity>, List<UsersItem?>>(){
            override fun loadFromDB(): Flow<List<UsersEntity>> =
                localDataSource.getSearchUsers()
            override fun shouldFetch(data: List<UsersEntity>?): Boolean =
                queryIsSame
            override suspend fun createCall(): Flow<ApiResponse<List<UsersItem?>>> =
                remoteDataSource.getSearchUsers(query)
            override suspend fun saveCallResult(data: List<UsersItem?>) =
                localDataSource.insertSearchUsers(
                    DataMapper.mapResponseToEntity(data)
                )
            override suspend fun deleteFromDB() {
                localDataSource.deleteSearchUsers()
            }
        }.asFlow()

    override fun getDetailUser(login: String): Flow<Resource<DetailUserResponse>> =
        object : NetworkBoundResource<DetailUserResponse, DetailUserResponse>(){
            override fun loadFromDB(): Flow<DetailUserResponse> =
                emptyFlow()
            override fun shouldFetch(data: DetailUserResponse?): Boolean =
                true
            override suspend fun createCall(): Flow<ApiResponse<DetailUserResponse>> =
                remoteDataSource.getDetailUser(login)
            override suspend fun saveCallResult(data: DetailUserResponse) {}
            override suspend fun deleteFromDB() {}
        }.asFlow()

    override fun getKeywordSearch(): Flow<String> =
        findPreference.getKeywordSearch()

    override suspend fun saveKeywordSearch(keyword: String) =
        findPreference.saveKeywordSearch(keyword)
}