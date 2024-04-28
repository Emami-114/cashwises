package ui.bottomBar

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import ui.components.customModiefier.noRippleClickable

@Composable
fun RowScope.TabNavigationItem(tab: Tab?, onClick: () -> Unit) {
    val tabNavigator = LocalTabNavigator.current
    var inter = MutableInteractionSource()
//    BottomNavigationItem(
//        selected = tabNavigator.current == tab,
//        onClick = {
//            if (tab != null) {
//                tabNavigator.current = tab
//            }
//            onClick()
//        },
//        icon = {
//            tab?.options?.icon?.let { icon ->
//                Icon(
//                    painter = icon,
//                    contentDescription = null
//                )
//            }
//        },
//        selectedContentColor = MaterialTheme.colorScheme.primary,
//        unselectedContentColor = MaterialTheme.colorScheme.secondary,
//        label = {
//            if (tab != null) {
//                Text(tab.options.title)
//
//            }
//        },
//        alwaysShowLabel = false,
//        interactionSource = remember { MutableInteractionSource() },
//        modifier = Modifier.background(Color.Transparent)
//    )
}