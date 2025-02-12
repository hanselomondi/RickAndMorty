package com.hansel.rickandmorty.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hansel.rickandmorty.R
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.presentation.components.CharacterCard
import com.hansel.rickandmorty.ui.theme.AppTheme
import com.hansel.rickandmorty.util.CustomPreview
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel = koinViewModel()
) {
    val screenState by characterViewModel.screenState.collectAsStateWithLifecycle()

    MainScreenContent(
        screenState = screenState
    )
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    screenState: ScreenState
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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
                                CharacterCard(character = character)
                        }
                    }
                }

                is ScreenState.Error -> {
                    Text(
                        text = screenState.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                    )
                }
            }
        }
    }
}

@CustomPreview
@Composable
private fun MainScreenContentPreview() {
    AppTheme {
        MainScreenContent(
            screenState = ScreenState.Error("No Network"),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}