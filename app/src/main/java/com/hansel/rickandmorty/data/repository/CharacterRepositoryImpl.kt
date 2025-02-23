package com.hansel.rickandmorty.data.repository

import com.hansel.rickandmorty.domain.datasource.RemoteDataSource
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.domain.model.NetworkResult
import com.hansel.rickandmorty.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : CharacterRepository {
    override suspend fun getCharacters(): NetworkResult<List<Character>> {
        return try {
            remoteDataSource.fetchCharacters()
        } catch (e: Exception) {
            NetworkResult.Error("${e.message}")
        }
    }

    override suspend fun getCharacterById(id: Int): NetworkResult<Character> {
        return try {
            remoteDataSource.fetchCharacterById(id)
        } catch (e: Exception) {
            NetworkResult.Error("${e.message}")
        }
    }
}