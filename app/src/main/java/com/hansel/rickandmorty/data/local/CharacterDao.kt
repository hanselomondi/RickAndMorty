package com.hansel.rickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characterEntity: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    fun getCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters")
    suspend fun clearAll()

    @Query("SELECT * FROM characters WHERE id=:id")
    fun getCharacterById(id: Int): Flow<CharacterEntity>

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Query("SELECT * FROM characters WHERE isFavourite = 1")
    fun getFavouriteCharacters(): Flow<List<CharacterEntity>>
}