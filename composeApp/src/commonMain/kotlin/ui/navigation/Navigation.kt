package ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import compose.icons.TablerIcons
import compose.icons.tablericons.Bell
import compose.icons.tablericons.Home
import compose.icons.tablericons.Search
import compose.icons.tablericons.User
import ui.account.AccountScreen
import ui.account.auth.AuthView
import ui.components.CustomBackgroundView
import ui.components.CustomTopAppBar
import ui.deals.DetailDealScreen
import ui.home.HomeScreen
import ui.menu.BottomBarView
import ui.menu.TabBarScreen
import ui.notification.NotificationView
import ui.search.SearchView

@Composable
fun NavHostMain(
    navController: NavHostController = rememberNavController(),
    onNavigate: (rootName: String) -> Unit
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination

    Scaffold(topBar = {
        if (currentScreen?.route != BottomBarScreen.Search.route && navController.shouldShowBottomBar)
            CustomTopAppBar(title = getTitle(currentScreen))
    },
        bottomBar = {
            if (navController.shouldShowBottomBar) {
                BottomBarView(currentScreen?.route ?: "", selectedTab = { route ->
                    navController.navigate(route)
                })
            }
        }
    ) { innerPadding ->
        Box {
            CustomBackgroundView()
            NavHost(
                navController = navController,
                startDestination = BottomBarScreen.Home.route,
                modifier = if (navController.shouldShowBottomBar) {
                    Modifier.fillMaxSize()
                        .padding(innerPadding)
                } else {
                    Modifier.fillMaxSize()
                },
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }
            ) {
                composable(route = BottomBarScreen.Home.route) {
                    HomeScreen(onNavigate = onNavigate)
                }
                composable(route = BottomBarScreen.Search.route) {
                    SearchView(onNavigate = onNavigate)
                }
                composable(route = BottomBarScreen.Notification.route) {
                    NotificationView(onNavigate = onNavigate)
                }
                composable(route = BottomBarScreen.Profile.route) {
                    AccountScreen(onNavigate = onNavigate)
                }
                composable(route = AppScreen.Detail.route) {
                    DetailDealScreen(onNavigate = onNavigate)
                }
                composable(route = AppScreen.Login.route) {
                    AuthView(onNavigate = onNavigate)
                }
                composable(route = AppScreen.Create.route) {
                    TabBarScreen(onNavigate)
                }
            }
        }
    }
}

fun NavigateTo(
    routeName: String,
    navController: NavController
) {
    when (routeName) {
        RouterBackNavigate.Back.route -> {
            navController.popBackStack()
        }

        else -> {
            navController.navigate(routeName)

        }
    }
}

private val NavController.shouldShowBottomBar
    get() = when (this.currentBackStackEntry?.destination?.route) {
        BottomBarScreen.Home.route,
        BottomBarScreen.Search.route,
        BottomBarScreen.Profile.route,
        BottomBarScreen.Notification.route,
        -> true

        else -> false
    }

private fun getTitle(currentScreen: NavDestination?): String {
    return when (currentScreen?.route) {
        BottomBarScreen.Home.route -> {
            BottomBarScreen.Home.title
        }

        BottomBarScreen.Search.route -> {
            BottomBarScreen.Search.title
        }

        BottomBarScreen.Notification.route -> {
            BottomBarScreen.Notification.title
        }

        BottomBarScreen.Profile.route -> {
            BottomBarScreen.Profile.title
        }

        else -> ""
    }
}

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomBarScreen(route = "HOME", title = "Home", icon = TablerIcons.Home)
    data object Search :
        BottomBarScreen(route = "SEARCH", title = "Search", icon = TablerIcons.Search)

    data object Notification :
        BottomBarScreen(route = "NOTIFICATION", title = "Notification", icon = TablerIcons.Bell)

    data object Profile :
        BottomBarScreen(route = "PROFILE", title = "Profile", icon = TablerIcons.User)
}

sealed class AppScreen(val route: String) {
    data object Detail : AppScreen("DEAL_DETAIL")
    data object Login : AppScreen("LOGIN")
    data object Create : AppScreen("CREATE_DEAL")
}

sealed class RouterBackNavigate(val route: String) {
    data object Back : RouterBackNavigate("back")
}