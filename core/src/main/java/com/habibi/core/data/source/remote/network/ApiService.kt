package com.habibi.core.data.source.remote.network

import com.habibi.core.data.source.remote.response.DetailUserResponse
import com.habibi.core.data.source.remote.response.SearchUsersResponse
import com.habibi.core.data.source.remote.response.UserRepositoryResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ): SearchUsersResponse

    @GET("users/{login}")
    suspend fun getDetailUser(
        @Path("login") login: String
    ): DetailUserResponse

    @GET("users/{login}/repos")
    suspend fun getUserRepository(
        @Path("login") login: String
    ): List<UserRepositoryResponseItem>

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): SearchUsersResponse
}