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
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.company.app.theme.cw_dark_background
import ui.bottomBarRouter
import ui.components.customModiefier.customBorder
import ui.menu.components.BottomBarItem

@Composable
fun BottomNavigationView(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onNavigate: (Any) -> Unit
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

        bottomBarRouter.forEach { tab ->
            BottomBarItem(
                modifier = Modifier,
                title = tab.name,
                icon = tab.icon,
                isSelected = currentDestination?.hierarchy?.any { it.hasRoute(tab.route::class) } == true
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onNavigate(tab.route)
            }
        }
    }
}