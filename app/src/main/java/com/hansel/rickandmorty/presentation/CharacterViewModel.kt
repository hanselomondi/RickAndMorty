package com.hansel.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansel.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _characterListState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val characterListState: StateFlow<ScreenState> = _characterListState
        .onStart { getCharacters() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            ScreenState.Loading
        )

    private val _characterDetailsState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val characterDetailsState: StateFlow<ScreenState> = _characterDetailsState
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            ScreenState.Loading
        )

    private fun getCharacters() = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.getCharacters()
            .onSuccess { characters ->
                _characterListState.update {
                    return@update ScreenState.Success(characters)
                }
            }
            .onError { errorMessage ->
                _characterListState.update {
                    return@update ScreenState.Error(errorMessage)
                }
            }
    }

    fun getCharacterById(id: Int) = viewModelScope.launch {
        characterRepository.getCharacterById(id)
            .onSuccess { character ->
                _characterDetailsState.update {
                    return@update ScreenState.Success(character)
                }
            }
            .onError { message ->
                _characterDetailsState.update {
                    return@update ScreenState.Error(message)
                }
            }
    }
}