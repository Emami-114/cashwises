package ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import org.company.app.theme.cw_dark_background
import ui.BottomBarScreen
import ui.components.customModiefier.customBorder
import ui.menu.components.BottomBarItem

@Composable
fun BottomNavigationView(
    modifier: Modifier = Modifier,
    currentScreenRouter: String,
    onNavigate: (String) -> Unit
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
        val listScreen = listOf(
            BottomBarScreen.Home,
            BottomBarScreen.Search,
            BottomBarScreen.Notification,
            BottomBarScreen.Account
        )
        listScreen.forEach { bottomBarScreen ->
            BottomBarItem(
                modifier = Modifier,
                tab = bottomBarScreen,
                isSelected = currentScreenRouter == bottomBarScreen.route
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onNavigate(bottomBarScreen.route)
            }
        }
    }
}