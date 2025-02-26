package com.hansel.rickandmorty.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hansel.rickandmorty.R
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.presentation.components.CharacterCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteCharacterListScreen(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel = koinViewModel(),
    onCardClicked: (Int) -> Unit
) {
    val favouriteCharacters by characterViewModel.favouriteCharacterList.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        characterViewModel.getFavouriteCharacters()
    }

    FavouriteCharacterListContent(
        favouriteCharacters = favouriteCharacters,
        onFavouriteClicked = {
            characterViewModel.updateCharacter(it)
        },
        onCardClicked = onCardClicked,
        modifier = modifier
    )
}

@Composable
fun FavouriteCharacterListContent(
    modifier: Modifier = Modifier,
    favouriteCharacters: List<Character>,
    onFavouriteClicked: (Character) -> Unit,
    onCardClicked: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = favouriteCharacters.isEmpty(),
            enter =  scaleIn() + slideInVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.no_favourites)
                )
            }
        }

        AnimatedVisibility(
            visible = favouriteCharacters.isNotEmpty(),
            enter = scaleIn() + slideInVertically()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                items(favouriteCharacters) { character ->
                    CharacterCard(
                        character = character,
                        onCardClicked = onCardClicked,
                        onFavouriteClicked = {
                            onFavouriteClicked(character.copy(isFavourite = !character.isFavourite))
                        }
                    )
                }
            }
        }
    }
}