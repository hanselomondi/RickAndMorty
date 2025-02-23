package com.hansel.rickandmorty.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.hansel.rickandmorty.presentation.CharacterDetailsScreen
import com.hansel.rickandmorty.presentation.CharacterListScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CharacterListRoute
    ) {
        composable<CharacterListRoute> {
            CharacterListScreen(
                onCardClicked = { id ->
                    navController.navigate(CharacterDetailsRoute(id))
                }
            )
        }

        composable<CharacterDetailsRoute> {
            val character = it.toRoute<CharacterDetailsRoute>()
            CharacterDetailsScreen(
                characterId = character.characterId
            )
        }
    }
}