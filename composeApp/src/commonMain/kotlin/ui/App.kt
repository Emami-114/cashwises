package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.russhwolf.settings.Settings
import theme.AppTheme
import ui.home.HomeTab
import ui.menu.BottomBarView
import ui.menu.BottomBarViewEnum

var settings = Settings()

@Composable
fun App() = AppTheme {
    var currentTab by remember { mutableStateOf(BottomBarViewEnum.HOME) }
    Column(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.captionBar)
            .background(
                MaterialTheme.colorScheme.background
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabNavigator(HomeTab,disposeNestedNavigators = true) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomBarView(currentTab) { currentTab = it }

//                    BottomNavigation(
//                        backgroundColor = Color.Black,
//                        modifier = Modifier.background(Color.Black).padding(bottom = 13.dp)
//                    ) {
//                        TabNavigationItem(HomeTab) {}
//                        TabNavigationItem(SearchTab) {}
//                        TabNavigationItem(NotificationTab) {}
//                        TabNavigationItem(AccountTab) {}
//                    }
                },
                content = {
                    CurrentTab()
                }
            )
        }
    }
}
