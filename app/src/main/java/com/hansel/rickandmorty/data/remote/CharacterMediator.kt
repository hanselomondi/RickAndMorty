package com.hansel.rickandmorty.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.hansel.rickandmorty.data.local.CharacterDatabase
import com.hansel.rickandmorty.data.local.CharacterEntity
import com.hansel.rickandmorty.data.mapper.toCharacterEntity
import com.hansel.rickandmorty.data.mapper.toDomainCharacter
import kotlinx.coroutines.delay
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterMediator(
    private val db: CharacterDatabase,
    private val apiService: RickAndMortyApi
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1  // Called each time the app is opened
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null)
                        return MediatorResult.Success(endOfPaginationReached = true)
                    else
                        (lastItem.id / state.config.pageSize) + 1
                }
            }

            delay(2500)  // Fake delay since loading from cache is quite fast
            val characters = apiService.fetchCharacters(
                page = page,
                itemsPerPage = state.config.pageSize
            )

            // Cache the fetched results
            db.withTransaction {
                // When the app is opened for the first time, clear the database to load new data
                if (loadType == LoadType.REFRESH) {
                    db.getCharacterDao().clearAll()
                }
                // Cache the fetched data
                val characterEntities = characters.results.map { characterDto ->
                    characterDto.toDomainCharacter().toCharacterEntity()
                }
                db.getCharacterDao().insertCharacters(characterEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = characters.results.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}