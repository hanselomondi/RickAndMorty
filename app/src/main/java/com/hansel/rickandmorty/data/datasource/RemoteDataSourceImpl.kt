package com.hansel.rickandmorty.data.datasource

import android.util.Log
import com.hansel.rickandmorty.data.mapper.toDomainCharacter
import com.hansel.rickandmorty.data.remote.RickAndMortyApi
import com.hansel.rickandmorty.domain.datasource.RemoteDataSource
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.domain.model.NetworkResult
import java.io.IOException

class RemoteDataSourceImpl(
    private val apiService: RickAndMortyApi
) : RemoteDataSource {
    override suspend fun fetchCharacters(): NetworkResult<List<Character>> {
        return try {
            val response = apiService.fetchCharacters()
            Log.d("fetchCharacters", "response: ${response.body().toString()}")
            if (response.isSuccessful) {
                NetworkResult.Success(
                    data = response.body()?.results?.map { characterDto ->
                        characterDto.toDomainCharacter()
                    } ?: emptyList()
                )
            } else {
                NetworkResult.Error(
                    message = response.errorBody().toString()
                )
            }
        } catch (e: IOException) {
            NetworkResult.Error(
                message = "Network Error: Check your internet connection"
            )
        } catch (e: Exception) {
            NetworkResult.Error(
                message = "${e.message}"
            )
        }
    }

    override suspend fun fetchCharacterById(characterId: Int): NetworkResult<Character> {
        TODO("Not yet implemented")
    }
}