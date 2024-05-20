package ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import compose.icons.TablerIcons
import compose.icons.tablericons.Bell
import compose.icons.tablericons.Home
import compose.icons.tablericons.Search
import compose.icons.tablericons.User
import ui.account.AccountView
import ui.account.auth.AuthView
import ui.components.CustomBackgroundView
import ui.components.CustomToast
import ui.components.CustomTopAppBar
import ui.deals.DealsView
import ui.deals.DetailDealScreen
import ui.home.HomeView
import ui.menu.BottomNavigationView
import ui.menu.TabBarScreen
import ui.notification.NotificationView
import ui.search.SearchScreen
import ui.search.SearchView

@Composable
fun HomeNav() {
    val navController = rememberNavController()
    NavHostMain(
        navController = navController,
        onNavigate = { routeName ->
            if (navController.currentBackStackEntry?.destination?.route != routeName) {
                navigateTo(routeName, navController)
            }
        }
    )
}

@Composable
fun NavHostMain(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onNavigate: (rootName: String) -> Unit,
) {

    val shoWAnimation by animateFloatAsState(
        targetValue = if (navController.shouldShowBottomBar) 1f else 0f,
        animationSpec = tween(200)
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination
    Scaffold(bottomBar = {
        if (navController.shouldShowBottomBar && shoWAnimation == 1f)
            BottomNavigationView(
                modifier = Modifier,
                currentScreenRouter = currentScreen?.route ?: ""
            ) { onNavigate(it) }
    }) { innerPadding ->
        CustomBackgroundView()


        NavHost(
            navController = navController,
            startDestination = BottomBarScreen.Home.route,
            modifier = Modifier
                .fillMaxSize(),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400)
                )
            }
        ) {
            composable(
                route = BottomBarScreen.Home.route,
                deepLinks = listOf(
                    NavDeepLink("https://cashwises.backend.api.cwcash.de")
                ),
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                }
            ) {
//                Box(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)) {
                HomeView(onNavigate)
//                }
            }
            composable(
                route = BottomBarScreen.Search.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                },

                ) {
                SearchScreen(onNavigate = onNavigate)
            }
            composable(
                route = BottomBarScreen.Notification.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                }
            ) {
                NotificationView(onNavigate)
            }
            composable(
                route = BottomBarScreen.Account.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                }
            ) {
                AccountView(onNavigate)
            }

            composable(route = AppScreen.Detail.route) {
                DetailDealScreen(onNavigate = onNavigate) { }
            }
            composable(route = AppScreen.Authentication.route) {
                AuthView(onNavigate)
            }
            composable(route = AppScreen.CreateDeal.route) {
                TabBarScreen(onNavigate = onNavigate)
            }
            composable(
                route = AppScreen.SearchView.route + "/{categoryId}",
                arguments = listOf(navArgument("categoryId") {
                    type = NavType.StringType
                    defaultValue = "Default argument"
                    nullable = true
                })
            ) {
                val categoryId = it.arguments?.getString("categoryId")
                println("test argument $categoryId")
                SearchView(argument = categoryId, onNavigate = onNavigate)

            }
        }
    }
}

fun navigateTo(
    routeName: String,
    navController: NavController
) {
    when (routeName) {
        AppConstants.BackClickRoute.route -> {
            navController.popBackStack()
        }

        else -> {
            navController.navigate(routeName)
        }
    }
}

sealed class AppConstants(val route: String) {
    data object BackClickRoute : AppConstants(route = "BACK_CLICK_ROUTE")
}

sealed class AppScreen(
    val route: String,
    var title: String,
) {
    data object Home : AppScreen(
        route = "HOME",
        title = "Home",
    )

    data object Detail : AppScreen(
        route = "Detail",
        title = "Detail",
    )

    data object CreateDeal : AppScreen(
        route = "CREATE",
        title = "Create",
    )

    data object Authentication : AppScreen(
        route = "AUTHENTICATION",
        title = "Auth",
    )

    data object Profile : AppScreen(
        route = "PROFILE",
        title = "Profile",
    )

    data object Settings : AppScreen(
        route = "SETTINGS",
        title = "Settings",
    )

    data object Imprint : AppScreen(
        route = "IMPRINT",
        title = "Imprint",
    )

    data object PrivacyPolicy : AppScreen(
        route = "PRIVACY_POLICY",
        title = "Privacy Policy",
    )

    data object SearchView : AppScreen(
        route = "SEARCH",
        title = "Search",
    )

}

sealed class BottomBarScreen(
    val route: String,
    var title: String,
    val defaultIcon: ImageVector
) {
    data object Home : BottomBarScreen(
        route = "HOME",
        title = "Home",
        defaultIcon = TablerIcons.Home,
    )

    data object Search : BottomBarScreen(
        route = "Search",
        title = "Search",
        defaultIcon = TablerIcons.Search,
    )

    data object Notification : BottomBarScreen(
        route = "Notification",
        title = "Notification",
        defaultIcon = TablerIcons.Bell,
    )

    data object Account : BottomBarScreen(
        route = "Account",
        title = "Account",
        defaultIcon = TablerIcons.User,
    )
}

private val NavController.shouldShowBottomBar
    get() = when (this.currentBackStackEntry?.destination?.route) {
        BottomBarScreen.Home.route,
        BottomBarScreen.Search.route,
        BottomBarScreen.Notification.route,
        BottomBarScreen.Account.route,
        -> true

        else -> false
    }
