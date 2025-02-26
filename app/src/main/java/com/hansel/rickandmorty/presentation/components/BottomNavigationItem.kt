package com.hansel.rickandmorty.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.hansel.rickandmorty.R
import com.hansel.rickandmorty.presentation.navigation.CharacterListRoute
import com.hansel.rickandmorty.presentation.navigation.Destination
import com.hansel.rickandmorty.presentation.navigation.FavouriteCharacterListRoute

data class BottomNavigationItem(
    @StringRes val label: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Destination
)

val bottomNavBarItems = listOf(
    BottomNavigationItem(
        label = R.string.home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = CharacterListRoute
    ),
    BottomNavigationItem(
        label = R.string.favourites,
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        route = FavouriteCharacterListRoute
    )
)
