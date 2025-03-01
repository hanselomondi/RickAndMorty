package com.hansel.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.domain.model.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<PagingData<Character>>

    suspend fun getCharacterById(id: Int): Flow<NetworkResult<Character>>

    suspend fun updateCharacter(character: Character)

    suspend fun getFavouriteCharacters(): Flow<List<Character>>
}