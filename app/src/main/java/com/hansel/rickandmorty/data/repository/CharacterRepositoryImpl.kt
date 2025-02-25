package com.hansel.rickandmorty.data.repository

import android.util.Log
import com.hansel.rickandmorty.domain.datasource.LocalDataSource
import com.hansel.rickandmorty.domain.datasource.RemoteDataSource
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.domain.model.NetworkResult
import com.hansel.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CharacterRepository {
    override suspend fun getCharacters(): Flow<NetworkResult<List<Character>>> {
        return localDataSource.getCharacters()
            .map { cachedCharacters ->
                Log.d("cachedCharacters", "$cachedCharacters")
                if (cachedCharacters.isEmpty()) {
                    val result = remoteDataSource.fetchCharacters()
                    if (result is NetworkResult.Success)
                        localDataSource.insertCharacters(result.data)
                    result
                } else {
                    NetworkResult.Success(data = cachedCharacters)
                }
            }
            .catch {
                emit(NetworkResult.Error(message = it.message ?: "Unknown Error"))
                Log.e("getCharacters", it.message ?: "Unknown Error")
            }
    }

    override suspend fun getCharacterById(id: Int): Flow<NetworkResult<Character>> {
        return localDataSource.getCharacterById(id)
            .map { character ->
                character?.let {
                    Log.d("cachedCharacter", "character: $it")
                    NetworkResult.Success(data = it)
                } ?: remoteDataSource.fetchCharacterById(id)
            }
            .catch { error ->
                Log.e("getCharacterById", error.message ?: "Unknown Error")
                emit(NetworkResult.Error(message = error.message ?: "Unknown Error"))
            }
    }
}