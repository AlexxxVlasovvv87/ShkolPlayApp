package ru.alexvlasov.shkolplay.presentation.base

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.alexvlasov.shkolplay.domain.model.UserRole
import ru.alexvlasov.shkolplay.presentation.navigation.NavigationInfo

@Composable
fun BottomNavigationBar(navController: NavController, role: UserRole?) {
    val items = when(role) {
        UserRole.Student -> NavigationInfo.studentBottomBarItems
        else -> NavigationInfo.teacherBottomBarItems
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var selected by remember { mutableStateOf(items.size - 1)}
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.route
                    )
                },
                label = { Text(stringResource(item.title)) },
                onClick = {
                    if (navController.currentDestination?.route != items[index].route) {
                        selected = index
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) //Удаление лишних копий
                            launchSingleTop = true
                            restoreState = selected != index
                        }
                    }
                },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                alwaysShowLabel = true
            )
        }
    }
}