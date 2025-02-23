package com.hansel.rickandmorty.data.mapper

import com.hansel.rickandmorty.data.remote.dto.CharacterDto
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.domain.model.CharacterStatus
import java.util.Locale

/**
 * Maps a [CharacterDto] object received from an API call, to a domain character
 * @return Character
 */
fun CharacterDto.toDomainCharacter(): Character {
    val characterStatus = when (status.lowercase(Locale.getDefault())) {
        "alive" -> CharacterStatus.Alive
        "dead" -> CharacterStatus.Dead
        else -> CharacterStatus.Unknown
    }

    return Character(
        id = id,
        name = name,
        status = characterStatus,
        species = species,
        type = type?.ifEmpty { null },
        location = Character.CharacterLocation(
            name = location.name,
            url = location.url?.ifEmpty { null }
        ),
        image = image,
        episodes = episodes ?: emptyList()
    )
}