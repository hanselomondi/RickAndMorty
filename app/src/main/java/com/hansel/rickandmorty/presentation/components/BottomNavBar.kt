package com.hansel.rickandmorty.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.hansel.rickandmorty.presentation.navigation.Destination

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onNavigate: (Destination) -> Unit
) {
    NavigationBar(
        modifier = modifier
    ) {
        bottomNavBarItems.forEach { item ->
            val selected = currentDestination?.hierarchy?.any {
                it.hasRoute(item.route::class)
            } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected)
                        onNavigate(item.route)
                },
                icon = {
                    val icon = if (selected) item.selectedIcon else item.unselectedIcon
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(item.label)
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.label)
                    )
                }
            )
        }
    }
}