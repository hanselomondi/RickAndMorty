package com.hansel.rickandmorty.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.hansel.rickandmorty.presentation.CharacterDetailsScreen
import com.hansel.rickandmorty.presentation.CharacterListScreen
import com.hansel.rickandmorty.presentation.FavouriteCharacterListScreen
import com.hansel.rickandmorty.presentation.components.BottomNavBar

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        modifier = modifier,
        bottomBar = {
            AnimatedVisibility(
                visible = currentDestination?.hierarchy?.any {
                    it.hasRoute(CharacterDetailsRoute::class)
                } == false,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavBar(
                    currentDestination = currentDestination,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CharacterListRoute,
            modifier = Modifier
                .padding(innerPadding)
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

            composable<FavouriteCharacterListRoute> {
                FavouriteCharacterListScreen(
                    onCardClicked = { id ->
                        navController.navigate(CharacterDetailsRoute(id))
                    }
                )
            }
        }
    }
}