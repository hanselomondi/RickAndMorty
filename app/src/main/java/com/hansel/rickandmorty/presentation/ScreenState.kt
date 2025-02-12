package com.hansel.rickandmorty.presentation

sealed interface ScreenState {
    data object Loading : ScreenState
    data class Success<T>(val data: T) : ScreenState
    data class Error(val message: String) : ScreenState
}