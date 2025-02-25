package com.hansel.rickandmorty.util

import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.domain.model.CharacterStatus

const val BASE_URL = "https://rickandmortyapi.com/api/"

val PREVIEW_CHARACTER = Character(
    id = 233,
    name = "Morty K-22",
    status = CharacterStatus.Alive,
    species = "Human",
    type = null,
    location = Character.CharacterLocation("kl", "kl"),
    image = "https=//rickandmortyapi.com/api/character/avatar/233.jpeg",
    episodes = listOf("https=//rickandmortyapi.com/api/episode/28"),
    isFavourite = true
)