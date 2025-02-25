package com.hansel.rickandmorty.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class CharacterStatus(val status: String) {
    @Serializable
    @SerialName("Alive")
    data object Alive : CharacterStatus("Alive")

    @Serializable
    @SerialName("Dead")
    data object Dead : CharacterStatus("Dead")

    @Serializable
    @SerialName("Unknown")
    data object Unknown : CharacterStatus("Unknown")
}
