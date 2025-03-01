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

    override suspend fun fetchCharacterById(characterId: Int): NetworkResult<Character> {
        return try {
            val response = apiService.fetchCharacterById(characterId)
            if (response.isSuccessful) {
                NetworkResult.Success(
                    data = response.body()!!.toDomainCharacter() // Assuming the body can never be empty on a successful network call
                )
            } else {
                NetworkResult.Error(
                    message = response.errorBody()?.toString() ?: "Unknown error"
                )
            }
        } catch (e: IOException) {
            NetworkResult.Error(
                message = "Network Error: Check your internet connection"
            )
        } catch (e: Exception) {
            NetworkResult.Error(
                message = "Unknown Error"
            )
        }
    }
}