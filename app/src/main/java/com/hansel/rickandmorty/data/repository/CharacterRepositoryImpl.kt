package com.hansel.rickandmorty.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.hansel.rickandmorty.data.local.CharacterEntity
import com.hansel.rickandmorty.data.mapper.toDomainCharacter
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
    private val localDataSource: LocalDataSource,
    private val pager: Pager<Int, CharacterEntity>
) : CharacterRepository {

    override fun getCharacters(): Flow<PagingData<Character>> {
        return pager
            .flow
            .map { pagingData ->
                pagingData.map { it.toDomainCharacter() }
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

    override suspend fun updateCharacter(character: Character) {
        localDataSource.updateCharacter(character)
    }

    override suspend fun getFavouriteCharacters(): Flow<List<Character>> {
        return localDataSource.getFavouriteCharacters()
    }
}