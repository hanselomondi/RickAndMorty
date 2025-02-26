package com.hansel.rickandmorty.presentation.navigation

import kotlinx.serialization.Serializable

interface Destination

@Serializable
object CharacterListRoute : Destination

@Serializable
data class CharacterDetailsRoute(val characterId: Int) : Destination

@Serializable
object FavouriteCharacterListRoute : Destination