package com.hansel.rickandmorty.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.domain.model.CharacterStatus

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String?,
    val location: Character.CharacterLocation,
    val image: String,
    val episodes: List<String>,
    @ColumnInfo(defaultValue = "0")
    val isFavourite: Boolean = false
)
