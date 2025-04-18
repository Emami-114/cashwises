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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.bell
import cashwises.composeapp.generated.resources.global_deals
import cashwises.composeapp.generated.resources.global_myAccount
import cashwises.composeapp.generated.resources.global_notification
import cashwises.composeapp.generated.resources.global_search
import cashwises.composeapp.generated.resources.global_wishList
import cashwises.composeapp.generated.resources.heart
import cashwises.composeapp.generated.resources.home
import cashwises.composeapp.generated.resources.push_new_deal_available
import cashwises.composeapp.generated.resources.push_new_deal_available_desc
import cashwises.composeapp.generated.resources.search
import cashwises.composeapp.generated.resources.user
import domain.model.AccountRoute
import domain.model.AuthenticationRoute
import domain.model.CreateDealRoute
import domain.model.DetailRoute
import domain.model.HomeRoute
import domain.model.ImprintRoute
import domain.model.NotificationsRoute
import domain.model.ProfileRoute
import domain.model.SearchResultRoute
import domain.model.SearchRoute
import domain.model.WishlistRoute
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
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
import ui.search.SearchResultView
import utils.LocalPushNotification
import utils.NavRouteExtension
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
        navController = navController
    )
}

@Composable
fun NavHostMain(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    val showAnimation by animateFloatAsState(
        targetValue = if (navController.shouldShowBottomBar) 1f else 0f,
        animationSpec = tween(200)
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination
    Scaffold(bottomBar = {
        if (navController.shouldShowBottomBar && showAnimation == 1f)
            BottomNavigationView(
                modifier = Modifier,
                currentScreenRouter = currentScreen?.route ?: "",
                navController = navController
            )
    }) { innerPadding ->
        CustomBackgroundView()
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
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
            composable<HomeRoute>(
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                }
            ) {
                HomeView(navController)
            }

            composable<SearchRoute>(
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                },
                ) {
                SearchScreen(navController = navController)
            }
            composable<SearchResultRoute>(
            ) { backStackEntry ->
                val args = backStackEntry.toRoute<SearchResultRoute>()
                SearchResultView(
                    categoryId = args.categoryId,
                    tagArgument = args.tag,
                    searchQuery = args.searchText,
                    title = args.title ?: "",
                    navController = navController
                )

            }
            composable<NotificationsRoute>(
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                }
            ) {
                NotificationView{}
            }
            composable<AccountRoute>(
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                }
            ) {
                AccountView(navController)
            }
            composable<DetailRoute>() {
                var args = it.toRoute<DetailRoute>()
                DealDetailScreen(dealId = args.id, navController = navController)
            }
            composable<AuthenticationRoute> {
                AuthView(navController = navController)
            }
            composable<CreateDealRoute> {
                CreateDealAndCategoriesScreen(navController = navController)
            }
            composable<WishlistRoute>(
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                }
            ) {
                WishListView(navController = navController)
            }
            composable<ProfileRoute> {
                ProfileView(navController = navController)
            }
            composable<ImprintRoute> {
                ImprintView(navController = navController)
            }
        }
    }
}

sealed class AppConstants(val route: String) {
    data object BackClickRoute : AppConstants(route = "BACK_CLICK_ROUTE")
}

sealed class BottomBarScreen(
    val route: String,
    var title: StringResource,
    val defaultIcon: DrawableResource
) {
    data object Home : BottomBarScreen(
        route = "HOME",
        title = Res.string.global_deals,
        defaultIcon = Res.drawable.home,
    )

    data object Search : BottomBarScreen(
        route = "Search",
        title = Res.string.global_search,
        defaultIcon = Res.drawable.search,
    )
    data object Wishlist: BottomBarScreen(
        route = "Wishlist",
        title = Res.string.global_wishList,
        defaultIcon = Res.drawable.heart,
    )
//    data object Notification : BottomBarScreen(
//        route = "Notification",
//        title = Res.string.global_notification,
//        defaultIcon = Res.drawable.bell,
//    )

    data object Account : BottomBarScreen(
        route = "Account",
        title = Res.string.global_myAccount,
        defaultIcon = Res.drawable.user,
    )
}

private val NavController.shouldShowBottomBar
    get() = when (this.currentBackStackEntry?.destination?.route) {
        customFindRoute(HomeRoute),
        customFindRoute(SearchRoute),
        customFindRoute(WishlistRoute),
        customFindRoute(AccountRoute),
            -> true

        else -> false
    }

fun NavController.customNavigate(route: Any) {
    if (this.currentBackStackEntry?.destination?.route != customFindRoute(route)) {
        println(this.findDestination(this.currentBackStackEntry?.destination?.route ?: ""))
        println(customFindRoute(route))
        this.navigate(route)
    }
}

fun NavController.customFindRoute(route: Any): String {
    val cleanedString = route.toString().replace(Regex("@[0-9a-fA-F]+$"), "")
    return cleanedString
}
