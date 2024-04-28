package ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import compose.icons.TablerIcons
import compose.icons.tablericons.Bell
import compose.icons.tablericons.Home
import compose.icons.tablericons.Search
import compose.icons.tablericons.User
import org.company.app.theme.cw_dark_background
import ui.account.AccountTab
import ui.components.customModiefier.customBorder
import ui.home.HomeTab
import ui.menu.components.BottomBarItem
import ui.notification.NotificationTab
import ui.search.SearchTab

@Composable
fun BottomBarView(currentTab: BottomBarViewEnum, selectedTab: (BottomBarViewEnum) -> Unit) {
    val tabNavigator = LocalTabNavigator.current

    Row(
        modifier = Modifier.padding(bottom = 20.dp).padding(horizontal = 15.dp).fillMaxWidth()
            .height(70.dp).customBorder(shape = MaterialTheme.shapes.extraLarge)
            .background(cw_dark_background, shape = MaterialTheme.shapes.extraLarge)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarViewEnum.entries.forEach { bottomBarViewEnum ->
            BottomBarItem(
                modifier = Modifier,
                title = bottomBarViewEnum.title,
                icon = bottomBarViewEnum.icon,
                currentTitle = currentTab.title
            ) {
                selectedTab(bottomBarViewEnum)
                tabNavigator.current = bottomBarViewEnum.tab
            }
        }
    }
}

enum class BottomBarViewEnum(
    val title: String, val icon: ImageVector, val tab: Tab
) {
    HOME(title = "Home", icon = TablerIcons.Home, tab = HomeTab),
    SEARCH(title = "Search", icon = TablerIcons.Search, tab = SearchTab),
    NOTIFICATION(title = "Notification", icon = TablerIcons.Bell, tab = NotificationTab),
    ACCOUNT(title = "Account", icon = TablerIcons.User, tab = AccountTab)
}