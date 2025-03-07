package com.hansel.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hansel.rickandmorty.domain.model.Character
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

    val characterList = characterRepository.getCharacters()
        .cachedIn(viewModelScope)

    private val _characterDetailsState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val characterDetailsState: StateFlow<ScreenState> = _characterDetailsState
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            ScreenState.Loading
        )

    private val _favouriteCharacterList = MutableStateFlow<List<Character>>(emptyList())
    val favouriteCharacterList: StateFlow<List<Character>> = _favouriteCharacterList
        .onStart { getFavouriteCharacters() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )


    fun getCharacterById(id: Int) = viewModelScope.launch {
        characterRepository.getCharacterById(id)
            .collect { result ->
                result
                    .onError { error ->
                        _characterDetailsState.update {
                            ScreenState.Error(message = error)
                        }
                    }
                    .onSuccess { character ->
                        _characterDetailsState.update {
                            ScreenState.Success(data = character)
                        }
                    }
            }
    }

    private fun getFavouriteCharacters() = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.getFavouriteCharacters().collect { favourites ->
            _favouriteCharacterList.update {
                favourites
            }
        }
    }

    fun updateCharacter(character: Character) = viewModelScope.launch {
        characterRepository.updateCharacter(character)
    }
}