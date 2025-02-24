package com.hansel.rickandmorty.data.local

import androidx.room.TypeConverter
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.domain.model.CharacterStatus
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CharacterTypeConverters {
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun convertStatusToString(status: CharacterStatus): String {
        return Json.encodeToString(status)
    }

    @TypeConverter
    fun convertStringToStatus(value: String): CharacterStatus {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun convertCharacterLocationToString(location: Character.CharacterLocation): String {
        return Json.encodeToString(location)
    }

    @TypeConverter
    fun convertStringToCharacterLocation(value: String): Character.CharacterLocation {
        return Json.decodeFromString(value)
    }
}