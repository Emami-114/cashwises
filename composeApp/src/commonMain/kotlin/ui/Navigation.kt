package ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.bell
import cashwises.composeapp.generated.resources.global_deals
import cashwises.composeapp.generated.resources.global_myAccount
import cashwises.composeapp.generated.resources.global_search
import cashwises.composeapp.generated.resources.global_wishList
import cashwises.composeapp.generated.resources.heart
import cashwises.composeapp.generated.resources.home
import cashwises.composeapp.generated.resources.push_new_deal_available
import cashwises.composeapp.generated.resources.push_new_deal_available_desc
import cashwises.composeapp.generated.resources.search
import cashwises.composeapp.generated.resources.user
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
import ui.search.SearchView
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
        navController = navController,
        onNavigate = { routeName ->
            navigateTo(routeName, navController)
        }
    )
}

@Composable
fun NavHostMain(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onNavigate: (routerScreen: Any) -> Unit,
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
                currentDestination = currentScreen
            ) { onNavigate(it) }
    }) { innerPadding ->
        CustomBackgroundView()
        NavHost(
            navController = navController,
            startDestination = Home,
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
            composable<Home>(
                deepLinks = listOf(
                    NavDeepLink("https://www.cwcash.de")
                ),
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                }
            ) {
//                Box(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)) {
                HomeView(onNavigateToDealDetail = { onNavigate(Detail(id = it)) })
//                }
            }
            composable<Detail> { navBackStackEntry ->
                val args = navBackStackEntry.toRoute<Detail>()
                DealDetailScreen(dealId = args.id) { navController.popBackStack() }
            }
            composable<Search>(
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                },
            ) {
                SearchScreen(onNavigate = onNavigate)
            }
            composable<Wishlist>(
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                }
            ) {
                WishListView(onNavigate = onNavigate)
            }
            composable<Account>(
                enterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200))
                }
            ) {
                AccountView(onNavigate)
            }
            composable<OnSearch> {
                val args = it.toRoute<OnSearch>()
                SearchView(
                    categoryId = args.categoryId,
                    tagArgument = args.tag,
                    searchQuery = args.searchText,
                    title = args.title ?: "",
                    onNavigate = onNavigate
                ) { navController.popBackStack() }
            }
            composable<Auth> {
                AuthView(onNavigate = onNavigate) { navController.popBackStack() }
            }
            composable<CreateDeal> {
                CreateDealAndCategoriesScreen { navController.popBackStack() }
            }
            composable<Profile> {
                ProfileView { navController.popBackStack() }
            }
            composable<Imprint> {
                ImprintView { navController.popBackStack() }
            }
        }
    }
}

fun navigateTo(
    routeScreen: Any,
    navController: NavController
) {
    if (!navController.visibleEntries.value.contains(navController.previousBackStackEntry)) {
        navController.navigate(routeScreen)
    }
}

sealed class AppConstants(val route: String) {
    data object BackClickRoute : AppConstants(route = "BACK_CLICK_ROUTE")
}

@Serializable
object Home

@Serializable
object Search

@Serializable
data class OnSearch(
    val categoryId: String? = null,
    val tag: String? = null,
    val searchText: String? = null,
    val title: String? = null
)

@Serializable
object Wishlist

@Serializable
object Account

@Serializable
object Auth

@Serializable
object CreateDeal

@Serializable
object Profile

@Serializable
object Imprint

@Serializable
object PrivacyPolicy

@Serializable
data class Detail(val id: String)

val bottomBarRouter = listOf(
    NavRouteExtension(name = Res.string.global_deals, route = Home, icon = Res.drawable.home),
    NavRouteExtension(name = Res.string.global_search, route = Search, icon = Res.drawable.search),
    NavRouteExtension(
        name = Res.string.global_wishList,
        route = Wishlist,
        icon = Res.drawable.heart
    ),
    NavRouteExtension(
        name = Res.string.global_myAccount,
        route = Account,
        icon = Res.drawable.user
    ),
)

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


private val NavController.shouldShowBottomBar
    get() = currentDestination?.hasRoute(Home::class) == true ||
            currentDestination?.hasRoute(Search::class) == true ||
            currentDestination?.hasRoute(Wishlist::class) == true ||
            currentDestination?.hasRoute(Account::class) == true
