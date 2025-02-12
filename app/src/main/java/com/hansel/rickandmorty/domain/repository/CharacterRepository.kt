package com.hansel.rickandmorty.domain.repository

import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.domain.model.NetworkResult

interface CharacterRepository {
    suspend fun getCharacters(): NetworkResult<List<Character>>

    suspend fun getCharacterById(id: Int): NetworkResult<Character>
}