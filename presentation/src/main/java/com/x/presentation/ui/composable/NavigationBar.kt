package com.x.presentation.ui.composable

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.x.presentation.navigation.NavigationBarItem
import com.x.presentation.ui.theme.ProvideRippleEffect
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.getString

@Composable
fun BottomNavigationBar(
    barItems: List<NavigationBarItem>,
    navController: NavController,
) {
    NavigationBar(
        containerColor = Theme.colors.surface,
        contentColor = contentColorFor(Theme.colors.surface)
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        barItems.forEach { item ->
            ProvideRippleEffect {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title.getString()
                        )
                    },
                    label = {
                        Text(
                            text = item.title.getString()
                        )
                    },
                    selected = currentRoute == item.route,
                    alwaysShowLabel = false,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
