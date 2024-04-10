package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.russhwolf.settings.Settings
import org.jetbrains.compose.resources.stringResource
import theme.AppTheme
import ui.account.AccountTab
import ui.bottomBar.TabNavigationItem
import ui.home.HomeTab
import ui.notification.NotificationTab
import ui.search.SearchTab

var settings = Settings()

@Composable
fun App() = AppTheme {
    Column(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.captionBar)
            .background(
                MaterialTheme.colorScheme.background
            ),
        horizontalAlignment = Alignment.CenterHorizontally

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
