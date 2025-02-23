package com.hansel.rickandmorty.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String?,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    @SerialName("episode")
    val episodes: List<String>?
) {
    @Serializable
    data class Location(
        val name: String,
        val url: String?
    )
}
