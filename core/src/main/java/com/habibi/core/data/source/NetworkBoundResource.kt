package com.habibi.core.data.source

import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.utils.EspressoIdlingResource
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {
        EspressoIdlingResource.increment()
        emit(Resource.Loading())
        if (shouldFetch()) {
            deleteFromDB()
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    EspressoIdlingResource.decrement()
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB()!!.map { Resource.Success(it!!) })
                }
                is ApiResponse.Empty -> {
                    EspressoIdlingResource.decrement()
                    emit(Resource.Empty())
                }
                is ApiResponse.Error -> {
                    EspressoIdlingResource.decrement()
                    onFetchFailed()
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        } else {
            EspressoIdlingResource.decrement()
            emitAll(loadFromDB()!!.map { Resource.Success(it!!) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract suspend fun deleteFromDB()

    protected abstract fun loadFromDB(): Flow<ResultType?>?

    protected abstract fun shouldFetch(): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}