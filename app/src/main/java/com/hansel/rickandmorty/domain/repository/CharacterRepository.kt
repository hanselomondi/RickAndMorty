package com.hansel.rickandmorty.domain.repository

import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.domain.model.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(): Flow<NetworkResult<List<Character>>>

    suspend fun getCharacterById(id: Int): Flow<NetworkResult<Character>>
}