package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import theme.AppTheme
import ui.account.AccountTab
import ui.bottomBar.TabNavigationItem
import ui.components.customModiefier.customBorder
import ui.home.HomeTab
import ui.menu.components.TabbarView
import ui.notification.NotificationTab
import ui.search.SearchTab

@Composable
fun App() = AppTheme {
    var tabbarExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize().background(
            MaterialTheme.colorScheme.background
        )
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        TabNavigator(HomeTab) {

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomNavigation(
                        backgroundColor = Color.Black,
                        modifier = Modifier.background(Color.Black).padding(bottom = 13.dp)
                    ) {
                        TabNavigationItem(HomeTab) {}
                        TabNavigationItem(SearchTab) {}
                        TabNavigationItem(NotificationTab) {}
                        TabNavigationItem(AccountTab) {}
                    }
                },
                content = { CurrentTab() }
            )
        }
    }


}
