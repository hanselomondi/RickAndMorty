package com.hansel.rickandmorty.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hansel.rickandmorty.R
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.presentation.components.ErrorMessage
import com.hansel.rickandmorty.presentation.components.StatusTag
import com.hansel.rickandmorty.ui.theme.AppTheme
import com.hansel.rickandmorty.util.CustomPreview
import com.hansel.rickandmorty.util.PREVIEW_CHARACTER
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterDetailsScreen(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel = koinViewModel(),
    characterId: Int
) {
    val screenState by characterViewModel.characterDetailsState.collectAsStateWithLifecycle()
    characterViewModel.getCharacterById(characterId)

    CharacterDetailsScreenContent(
        modifier = modifier
            .fillMaxSize(),
        screenState = screenState
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharacterDetailsScreenContent(
    modifier: Modifier = Modifier,
    screenState: ScreenState
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.padding_extra_small))
        ) {
            when (screenState) {
                is ScreenState.Loading -> {
                    CircularProgressIndicator()
                }

                is ScreenState.Success<*> -> {
                    val character = screenState.data as Character
                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(dimensionResource(R.dimen.padding_medium))
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(character.image)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Image of ${character.name}",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(300.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_medium)))
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = dimensionResource(R.dimen.padding_large))
                            ) {
                                Text(
                                    text = character.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_large)))
                                StatusTag(status = character.status)
                            }
                            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                            CharacterDetailComponent(
                                label = stringResource(R.string.species_label),
                                detail = character.species
                            )
                            CharacterDetailComponent(
                                label = stringResource(R.string.origin_label),
                                detail = character.species
                            )
                            CharacterDetailComponent(
                                label = stringResource(R.string.type_label),
                                detail = character.type ?: stringResource(R.string.unknown)
                            )
                            Text(
                                text = stringResource(R.string.episodes_label),
                                style = MaterialTheme.typography.bodySmall
                            )
                            if (character.episodes.isNotEmpty()) {
                                FlowRow {
                                    character.episodes.forEach { episode ->
                                        SuggestionChip(
                                            onClick = {},
                                            label = {
                                                Text(
                                                    text = episode.substringAfter("episode/")
                                                )
                                            },
                                            colors = SuggestionChipDefaults.suggestionChipColors(
                                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                                            ),
                                            shape = RoundedCornerShape(100f)
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = stringResource(R.string.none)
                                )
                            }
                        }
                    }
                }

                is ScreenState.Error -> {
                    ErrorMessage(
                        message = screenState.message,
                        textColor = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterDetailComponent(
    modifier: Modifier = Modifier,
    label: String,
    detail: String
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = detail
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
    }
}

@CustomPreview
@Composable
private fun CharacterDetailsScreenPreview() {
    AppTheme {
        CharacterDetailsScreenContent(
            screenState = ScreenState.Success(PREVIEW_CHARACTER)
        )
    }
}