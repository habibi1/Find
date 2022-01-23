package com.habibi.core.data.source.remote.network

import com.habibi.core.data.source.remote.response.DetailUserResponse
import com.habibi.core.data.source.remote.response.SearchUsersResponse
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

}