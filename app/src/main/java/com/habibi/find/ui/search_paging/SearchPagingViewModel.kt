package com.habibi.find.ui.search_paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.habibi.core.data.source.IGithubDataSource
import com.habibi.core.data.source.remote.response.UsersItem
import kotlinx.coroutines.flow.Flow

class SearchPagingViewModel(private val iGithubDataSource: IGithubDataSource) : ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<UsersItem>>? = null

    fun searchRepo(queryString: String): Flow<PagingData<UsersItem>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<UsersItem>> = iGithubDataSource.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}