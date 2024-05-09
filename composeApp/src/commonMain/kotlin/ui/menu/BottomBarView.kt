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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import compose.icons.TablerIcons
import compose.icons.tablericons.Bell
import compose.icons.tablericons.Home
import compose.icons.tablericons.Search
import compose.icons.tablericons.User
import org.company.app.theme.cw_dark_background
import ui.account.AccountScreenTab
import ui.components.customModiefier.customBorder
import ui.home.HomeScreenTab
import ui.menu.components.BottomBarItem
import ui.notification.NotificationScreenTab
import ui.search.SearchScreenTab

@Composable
fun BottomNavigationView(
    modifier: Modifier = Modifier,
    currentScreen: (BottomBarScreens),
    onNavigate: (BottomBarScreens) -> Unit
) {

    Row(
        modifier = modifier.padding(bottom = 20.dp).padding(horizontal = 15.dp).fillMaxWidth()
            .height(70.dp).customBorder(shape = MaterialTheme.shapes.extraLarge)
            .background(cw_dark_background, shape = MaterialTheme.shapes.extraLarge)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarScreens.entries.forEach { bottomBarScreen ->
            BottomBarItem(
                modifier = Modifier,
                tab = bottomBarScreen,
                isSelected = currentScreen.title == bottomBarScreen.title
            ) {
                onNavigate(it)
            }
        }
    }
}

enum class BottomBarScreens(val title: String, val icon: ImageVector) {
    Home(title = "Home", icon = TablerIcons.Home),
    Search(title = "Search", icon = TablerIcons.Search),
    Notification(title = "Notification", icon = TablerIcons.Bell),
    Account(title = "Account", icon = TablerIcons.User)
}