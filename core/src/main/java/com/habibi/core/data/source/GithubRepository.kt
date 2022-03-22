package com.habibi.core.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.habibi.core.data.source.remote.GithubPagingSource
import com.habibi.core.data.source.remote.network.ApiService
import com.habibi.core.data.source.remote.response.UsersItem
import kotlinx.coroutines.flow.Flow

class GithubRepository(private val apiService: ApiService): IGithubDataSource {

    override fun getSearchResultStream(query: String): Flow<PagingData<UsersItem>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { GithubPagingSource(apiService, query) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}