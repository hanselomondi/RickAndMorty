package com.hansel.rickandmorty.domain.model

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String): NetworkResult<Nothing>()

    fun onSuccess(block: (T) -> Unit): NetworkResult<T> {
        if (this is Success) block(data)
        return this
    }

    fun onError(block: (String) -> Unit): NetworkResult<T> {
        if (this is Error) block(message)
        return this
    }
}