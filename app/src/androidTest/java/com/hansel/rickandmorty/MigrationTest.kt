package com.hansel.rickandmorty

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hansel.rickandmorty.data.local.CharacterDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    private val TEST_DB = "test-db"

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        CharacterDatabase::class.java,
        emptyList(),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migration1To2_containsCorrectData() {
        var db = helper.createDatabase(TEST_DB, version = 2).apply {
            execSQL(
                """
                INSERT INTO characters (id, name, status, species, type, location, image, episodes) 
            VALUES (
                1, 
                'Rick Sanchez', 
                'Alive', 
                'Human', 
                'Parasite', 
                '{"name":"Citadel of Ricks","url":"https://rickandmortyapi.com/api/location/3"}', 
                'https://rickandmortyapi.com/api/character/avatar/1.jpeg', 
                '["https://rickandmortyapi.com/api/episode/1"]'
            )
            """.trimIndent()
            )
            close()
        }

        db = helper.runMigrationsAndValidate(TEST_DB, version = 2, true)
    }
}