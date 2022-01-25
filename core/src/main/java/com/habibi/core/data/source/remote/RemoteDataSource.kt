package com.habibi.core.data.source.remote

import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.data.source.remote.network.ApiService
import com.habibi.core.data.source.remote.response.DetailUserResponse
import com.habibi.core.data.source.remote.response.UserRepositoryResponseItem
import com.habibi.core.data.source.remote.response.UsersItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(private val apiService: ApiService) {

    suspend fun getSearchUsers(query: String): Flow<ApiResponse<List<UsersItem?>>> =
        flow {
            try {
                val response = apiService.getSearchUsers(query)
                emit(
                    if (response.totalCount == 0)
                        ApiResponse.Empty(null)
                    else
                        ApiResponse.Success(response.items!!)
                )
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailUser(login: String): Flow<ApiResponse<DetailUserResponse>> =
        flow {
            try {
                val response = apiService.getDetailUser(login)
                if (response.login != null)
                    emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(
                    ApiResponse.Error(e.toString())
                )
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getUserRepository(login: String): Flow<ApiResponse<List<UserRepositoryResponseItem?>>> =
        flow {
            try {
                val response = apiService.getUserRepository(login)
                emit(
                    if (response.isEmpty())
                        ApiResponse.Empty(null)
                    else
                        ApiResponse.Success(response)
                )
            } catch (e: Exception) {
                emit(
                    ApiResponse.Error(e.toString())
                )
            }
        }.flowOn(Dispatchers.IO)

}