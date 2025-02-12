package com.hansel.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansel.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val screenState: StateFlow<ScreenState> = _screenState
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            ScreenState.Loading
        )

    init {
        getCharacters()
    }

    private fun getCharacters() = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.getCharacters()
            .onSuccess { characters ->
                _screenState.update {
                    return@update ScreenState.Success(characters)
                }
            }
            .onError { errorMessage ->
                _screenState.update {
                    return@update ScreenState.Error(errorMessage)
                }
            }
    }
}