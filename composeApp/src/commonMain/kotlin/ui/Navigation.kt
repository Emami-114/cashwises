package ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.bell
import cashwises.composeapp.generated.resources.home
import cashwises.composeapp.generated.resources.push_new_deal_available
import cashwises.composeapp.generated.resources.push_new_deal_available_desc
import cashwises.composeapp.generated.resources.search
import cashwises.composeapp.generated.resources.user
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import ui.account.AccountView
import ui.account.auth.AuthView
import ui.account.imprint.ImprintView
import ui.account.profile.ProfileView
import ui.account.wish_list.WishListView
import ui.components.CustomBackgroundView
import ui.deals.DealDetailScreen
import ui.home.HomeView
import ui.menu.BottomNavigationView
import ui.menu.CreateDealAndCategoriesScreen
import ui.notification.NotificationView
import ui.search.SearchScreen
import ui.search.SearchView
import utils.LocalPushNotification
import utils.PushNotificationModel

@Composable
fun HomeNav() {
    val navController = rememberNavController()
    val pushTitle = stringResource(Res.string.push_new_deal_available)
    val pushTitleDesc = stringResource(Res.string.push_new_deal_available_desc)
    LaunchedEffect(Unit) {
        LocalPushNotification.schedule(
            pushNotificationModel = PushNotificationModel(
                identifier = "PuSH_NOTIFICATION",
                title = pushTitle,
                body = pushTitleDesc,
                timeInterval = 64800.0,
                repeats = true
            )
        )
    }
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

            composable(
                route = AppScreen.DealDetail.route + "/{deal_id}",
                arguments = listOf(navArgument("deal_id") {
                    type = NavType.StringType
                    nullable = true
                })
            ) {
                val dealId = it.arguments?.getString("deal_id")
                DealDetailScreen(dealId = dealId, onNavigate = onNavigate)
            }
            composable(route = AppScreen.Authentication.route) {
                AuthView(onNavigate)
            }
            composable(route = AppScreen.CreateDeal.route) {
                CreateDealAndCategoriesScreen(onNavigate = onNavigate)
            }
            composable(route = AppScreen.WishList.route) {
                WishListView(onNavigate = onNavigate)
            }
            composable(
                route = AppScreen.SearchView.route + "?categoryId={categoryId}&tag={tag}&title={title}&query={query}",
                arguments = listOf(
                    navArgument("categoryId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },
                    navArgument("tag") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },
                    navArgument("title") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },
                    navArgument("query") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },
                )
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString("categoryId")
                val tag = backStackEntry.arguments?.getString("tag")
                val title = backStackEntry.arguments?.getString("title")
                val query = backStackEntry.arguments?.getString("query")
                SearchView(
                    categoryId = categoryId,
                    tagArgument = tag,
                    searchQuery = query,
                    title = title ?: "",
                    onNavigate = onNavigate
                )

            }

            composable(route = AppScreen.Profile.route) {
                ProfileView(onNavigate = onNavigate)
            }
            composable(route = AppScreen.Imprint.route) {
                ImprintView(onNavigate = onNavigate)
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

    data object DealDetail : AppScreen(
        route = "DEAL_DETAIL",
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

    data object WishList : AppScreen(
        route = "WishList",
        title = "Wish list",
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
    val defaultIcon: DrawableResource
) {
    data object Home : BottomBarScreen(
        route = "HOME",
        title = "Home",
        defaultIcon = Res.drawable.home,
    )

    data object Search : BottomBarScreen(
        route = "Search",
        title = "Search",
        defaultIcon = Res.drawable.search,
    )

    data object Notification : BottomBarScreen(
        route = "Notification",
        title = "Notification",
        defaultIcon = Res.drawable.bell,
    )

    data object Account : BottomBarScreen(
        route = "Account",
        title = "Account",
        defaultIcon = Res.drawable.user,
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
