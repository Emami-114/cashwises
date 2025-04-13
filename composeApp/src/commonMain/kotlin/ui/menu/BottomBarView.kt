package ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.allStringArrayResources
import cashwises.composeapp.generated.resources.allStringResources
import cashwises.composeapp.generated.resources.title
import domain.model.AccountRoute
import domain.model.HomeRoute
import domain.model.NotificationsRoute
import domain.model.SearchRoute
import org.company.app.theme.cw_dark_background
import ui.BottomBarScreen
import ui.components.customModiefier.customBorder
import ui.customFindRoute
import ui.customNavigate
import ui.menu.components.BottomBarItem

@Composable
fun BottomNavigationView(
    modifier: Modifier = Modifier,
    currentScreenRouter: String,
    navController: NavHostController,
) {
    val haptic = LocalHapticFeedback.current

    Row(
        modifier = modifier.padding(bottom = 20.dp).padding(horizontal = 15.dp).fillMaxWidth()
            .height(70.dp).customBorder(shape = MaterialTheme.shapes.extraLarge)
            .background(cw_dark_background, shape = MaterialTheme.shapes.extraLarge)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var bottomBarScreen = listOf(
            BottomBarScreen.Home,
            BottomBarScreen.Search,
            BottomBarScreen.Notification,
            BottomBarScreen.Account
        )

        bottomBarScreen.forEach { tab ->
            BottomBarItem(
                modifier = Modifier,
                title = tab.title,
                icon = tab.defaultIcon,
                isSelected =
                    when(tab) {
                        BottomBarScreen.Home -> currentScreenRouter == navController.customFindRoute(HomeRoute)
                        BottomBarScreen.Search -> currentScreenRouter == navController.customFindRoute(SearchRoute)
                        BottomBarScreen.Notification -> currentScreenRouter == navController.customFindRoute(NotificationsRoute)
                        BottomBarScreen.Account -> currentScreenRouter == navController.customFindRoute(AccountRoute)
                    }

            ) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                // onNavigate(bottomBarScreen.route)
                navController.customNavigate(
                    when(tab) {
                        BottomBarScreen.Home -> HomeRoute
                        BottomBarScreen.Search -> SearchRoute
                        BottomBarScreen.Notification -> NotificationsRoute
                        BottomBarScreen.Account -> AccountRoute
                    }
                )
            }
        }
    }
}