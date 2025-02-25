package com.hansel.rickandmorty.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object CharacterListRoute

@Serializable
data class CharacterDetailsRoute(val characterId: Int)

@Serializable
object FavouriteCharacterListRoute