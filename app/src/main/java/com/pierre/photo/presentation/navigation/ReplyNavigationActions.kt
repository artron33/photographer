package com.pierre.photo.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.pierre.photo.R

object ReplyRoute {
    const val FAVORITES = "Favorites"
    const val SEARCH = "Search"
    const val COMING_SOON = "ComingSoon"
}

data class ReplyTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

class ReplyNavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: ReplyTopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    ReplyTopLevelDestination(
        route = ReplyRoute.FAVORITES,
        selectedIcon = Icons.Default.Star,
        unselectedIcon = Icons.Default.StarBorder,
        iconTextId = R.string.tab_favorite
    ),
    ReplyTopLevelDestination(
        route = ReplyRoute.COMING_SOON,
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        iconTextId = R.string.tab_account
    ),
    ReplyTopLevelDestination(
        route = ReplyRoute.SEARCH,
        selectedIcon = Icons.Outlined.Search,
        unselectedIcon = Icons.Default.Search,
        iconTextId = R.string.tab_search
    ),

)
