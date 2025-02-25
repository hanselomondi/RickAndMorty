package com.hansel.rickandmorty.data.datasource

import android.util.Log
import com.hansel.rickandmorty.data.local.CharacterDao
import com.hansel.rickandmorty.data.mapper.toCharacterEntity
import com.hansel.rickandmorty.data.mapper.toDomainCharacter
import com.hansel.rickandmorty.domain.datasource.LocalDataSource
import com.hansel.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class LocalDataSourceImpl(
    private val characterDao: CharacterDao
) : LocalDataSource {
    override suspend fun insertCharacters(characters: List<Character>) {
        characters.map { character ->
            characterDao.insertCharacter(character.toCharacterEntity())
        }
    }

    override fun getCharacters(): Flow<List<Character>> {
        return characterDao.getCharacters().map { list ->
            list.map { characterEntity ->
                characterEntity.toDomainCharacter()
            }
        }
    }

    override fun getCharacterById(id: Int): Flow<Character?> {
        return characterDao.getCharacterById(id).map {
            it.toDomainCharacter()
        }.onEach { Log.d("RoomFlow", "Emitting character: $it") }
    }
}