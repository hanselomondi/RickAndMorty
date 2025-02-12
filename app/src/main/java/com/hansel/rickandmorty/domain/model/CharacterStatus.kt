package com.hansel.rickandmorty.domain.model

sealed class CharacterStatus(val status: String) {
    data object Alive : CharacterStatus("Alive")
    data object Dead : CharacterStatus("Dead")
    data object Unknown : CharacterStatus("Unknown")
}