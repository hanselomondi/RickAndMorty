package com.hansel.rickandmorty.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CharacterEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to =  2)
    ]
)
@TypeConverters(CharacterTypeConverters::class)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
}