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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.russhwolf.settings.Settings
import theme.AppTheme
import ui.account.AccountScreenTab
import ui.account.AccountView
import ui.home.HomeScreen
import ui.home.HomeScreenTab
import ui.home.HomeView
import ui.menu.BottomBarScreens
import ui.menu.BottomNavigationView
import ui.notification.NotificationScreenTab
import ui.notification.NotificationView
import ui.search.SearchScreenTab
import ui.search.SearchView
import utils.LocalPushNotification

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
        var currentScreen by remember { mutableStateOf(BottomBarScreens.Home) }
//        LaunchedEffect(Unit) {
//            LocalPushNotification.requestAuthorization {  }
//        }
        TabNavigator(tab = HomeScreenTab) { _ ->
            val navigator = LocalNavigator.current
            Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                BottomNavigationView(currentScreen = currentScreen) { currentScreen = it }
            }) {
                when (currentScreen) {
                    BottomBarScreens.Home -> {
                        HomeView { }
                    }

                    BottomBarScreens.Search -> {
                        SearchView { }
                    }

                    BottomBarScreens.Notification -> {
                        NotificationView {
                        }
                    }

                    BottomBarScreens.Account -> {
                        AccountView { }
                    }
                }
            }
        }
    }

}

val Navigator.shouldShowBottomBar
    get() = when (this.items.last().key) {
        HomeScreenTab.key,
        NotificationScreenTab.key,
        SearchScreenTab.key,
        AccountScreenTab.key,
        -> true

        else -> {
            false
        }
    }