package com.hansel.rickandmorty.domain.datasource

import com.hansel.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getCharacterById(id: Int): Flow<Character?>

    suspend fun updateCharacter(character: Character)

    fun getFavouriteCharacters(): Flow<List<Character>>
}