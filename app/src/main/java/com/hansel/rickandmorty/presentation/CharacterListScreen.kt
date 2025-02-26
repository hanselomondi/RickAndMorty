package com.hansel.rickandmorty.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hansel.rickandmorty.R
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.presentation.components.CharacterCard
import com.hansel.rickandmorty.presentation.components.ErrorMessage
import com.hansel.rickandmorty.ui.theme.AppTheme
import com.hansel.rickandmorty.util.CustomPreview
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel = koinViewModel(),
    onCardClicked: (Int) -> Unit
) {
    val screenState by characterViewModel.characterListState.collectAsStateWithLifecycle()

    CharacterListContent(
        screenState = screenState,
        onCardClicked = onCardClicked,
        onFavouriteClicked = { character ->
            characterViewModel.updateCharacter(character)
        },
        modifier = modifier
    )
}

@Composable
fun CharacterListContent(
    modifier: Modifier = Modifier,
    screenState: ScreenState,
    onCardClicked: (Int) -> Unit,
    onFavouriteClicked: (Character) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (screenState) {
            is ScreenState.Loading -> {
                CircularProgressIndicator()
            }

            is ScreenState.Success<*> -> {
                val characters = screenState.data as? List<Character> ?: emptyList()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                ) {
                    items(characters) { character ->
                        CharacterCard(
                            character = character,
                            onCardClicked = onCardClicked,
                            onFavouriteClicked = onFavouriteClicked
                        )
                    }
                }
            }

            is ScreenState.Error -> {
                ErrorMessage(
                    message = screenState.message
                )
            }
        }
    }
}

@CustomPreview
@Composable
private fun MainScreenContentPreview() {
    AppTheme {
        CharacterListContent(
            screenState = ScreenState.Error("Network Error: Check your internet connection"),
            onCardClicked = {},
            onFavouriteClicked = {},
            modifier = Modifier
                .fillMaxSize()
        )
    }
}