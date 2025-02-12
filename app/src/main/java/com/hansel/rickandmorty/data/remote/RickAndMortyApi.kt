package com.hansel.rickandmorty.data.remote

import com.hansel.rickandmorty.data.remote.dto.CharacterDto
import com.hansel.rickandmorty.data.remote.dto.CharacterResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {
    @GET("character")
    suspend fun fetchCharacters(): Response<CharacterResponseDto>

    @GET("character/{id}")
    suspend fun fetchCharacterById(
        @Path("id") characterId: Int
    ): Response<CharacterDto>
}