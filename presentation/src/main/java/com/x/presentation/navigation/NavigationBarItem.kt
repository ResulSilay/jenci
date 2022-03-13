package com.x.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ViewStream
import androidx.compose.material.icons.rounded.DeviceHub
import androidx.compose.ui.graphics.vector.ImageVector
import com.x.presentation.R

sealed class NavigationBarItem(
    val route: String,
    val title: Int,
    val icon: ImageVector,
) {
    object Home : NavigationBarItem(
        route = Screen.Main.DashboardScreen.route,
        title = R.string.menu_home,
        icon = Icons.Default.Dashboard
    )

    object Node : NavigationBarItem(
        route = Screen.Main.Node.NodeScreen.route,
        title = R.string.menu_node,
        icon = Icons.Rounded.DeviceHub
    )

    object View : NavigationBarItem(
        route = Screen.Main.ViewScreen.route,
        title = R.string.menu_view,
        icon = Icons.Default.ViewStream
    )

    object Profile : NavigationBarItem(
        route = Screen.Main.SettingsScreen.route,
        title = R.string.menu_settings,
        icon = Icons.Default.Settings
    )
}