package com.hansel.rickandmorty.domain.datasource

import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.domain.model.NetworkResult

interface RemoteDataSource {
    suspend fun fetchCharacters(): NetworkResult<List<Character>>

    suspend fun fetchCharacterById(characterId: Int): NetworkResult<Character>
}