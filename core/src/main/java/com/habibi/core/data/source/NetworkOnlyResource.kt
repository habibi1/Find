package com.habibi.core.data.source

import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.utils.EspressoIdlingResource
import kotlinx.coroutines.flow.*

abstract class NetworkOnlyResource<ResultType> {

    private var result: Flow<Resource<ResultType>> = flow {
        EspressoIdlingResource.increment()
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                EspressoIdlingResource.decrement()
                emit(Resource.Success(apiResponse.data))
            }
            is ApiResponse.Empty -> {
                EspressoIdlingResource.decrement()
                emit(Resource.Empty())
            }
            else -> {
                EspressoIdlingResource.decrement()
                emit(Resource.Error(""))
            }
        }
    }

    protected abstract suspend fun createCall(): Flow<ApiResponse<ResultType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}