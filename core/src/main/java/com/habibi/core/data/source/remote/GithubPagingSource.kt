package com.habibi.core.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.habibi.core.data.source.GithubRepository.Companion.NETWORK_PAGE_SIZE
import com.habibi.core.data.source.remote.network.ApiService
import com.habibi.core.data.source.remote.response.UsersItem
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, UsersItem>() {
    override fun getRefreshKey(state: PagingState<Int, UsersItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersItem> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query
        return try {
            val response = apiService.getSearchUsers(apiQuery, position, params.loadSize)
            val repos = response.items
            val nextKey = if (repos.isEmpty()){
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else -1,
                nextKey = nextKey
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }
    }
}