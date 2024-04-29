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
import compose.icons.TablerIcons
import compose.icons.tablericons.Bell
import compose.icons.tablericons.Home
import compose.icons.tablericons.Search
import compose.icons.tablericons.User
import org.company.app.theme.cw_dark_background
import ui.components.customModiefier.customBorder
import ui.menu.components.BottomBarItem

@Composable
fun BottomBarView(currentTab: BottomBarViewEnum, selectedTab: (BottomBarViewEnum) -> Unit) {
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
            }
        }
    }
}

enum class BottomBarViewEnum(
    val title: String, val icon: ImageVector
) {
    HOME(title = "Home", icon = TablerIcons.Home),
    SEARCH(title = "Search", icon = TablerIcons.Search),
    NOTIFICATION(title = "Notification", icon = TablerIcons.Bell),
    ACCOUNT(title = "Account", icon = TablerIcons.User)
}