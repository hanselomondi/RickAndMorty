package com.hansel.rickandmorty.domain.model

sealed class CharacterGender(val gender: String) {
    data object Male : CharacterGender("Male")
    data object Female : CharacterGender("Female")
    data object Unknown : CharacterGender("Unknown")
    data object Genderless : CharacterGender("Genderless")
}