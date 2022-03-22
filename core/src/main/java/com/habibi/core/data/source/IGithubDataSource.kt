package com.habibi.core.data.source

import androidx.paging.PagingData
import com.habibi.core.data.source.remote.response.UsersItem
import kotlinx.coroutines.flow.Flow

interface IGithubDataSource {
    fun getSearchResultStream(query: String): Flow<PagingData<UsersItem>>
}