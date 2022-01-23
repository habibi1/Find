package com.habibi.core.data.source.remote.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Empty(val message: String?) : ApiResponse<Nothing>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
}