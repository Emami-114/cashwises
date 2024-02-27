package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import theme.AppTheme
import ui.account.AccountTab
import ui.bottomBar.TabNavigationItem
import ui.home.HomeTab
import ui.notification.NotificationTab
import ui.search.SearchTab

@Composable
fun App() = AppTheme {
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
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(SearchTab)
                        TabNavigationItem(NotificationTab)
                        TabNavigationItem(AccountTab)
                    }
                },
                content = { CurrentTab() }
            )
        }
    }

}
