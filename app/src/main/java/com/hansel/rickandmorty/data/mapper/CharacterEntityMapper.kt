package com.hansel.rickandmorty.data.mapper

import com.hansel.rickandmorty.data.local.CharacterEntity
import com.hansel.rickandmorty.domain.model.Character

fun CharacterEntity.toDomainCharacter(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        location = location,
        image = image,
        episodes = episodes,
        isFavourite = isFavourite
    )
}

fun Character.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        location = location,
        image = image,
        episodes = episodes,
        isFavourite = isFavourite
    )
}