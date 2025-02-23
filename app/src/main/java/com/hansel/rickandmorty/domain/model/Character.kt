package com.hansel.rickandmorty.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String?,
    val location: CharacterLocation,
    val image: String,
    val episodes: List<String>
) {
    data class CharacterLocation(
        val name: String,
        val url: String?
    )
}
