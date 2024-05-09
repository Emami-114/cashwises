package ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import compose.icons.TablerIcons
import compose.icons.tablericons.Home
import compose.icons.tablericons.Plus
import ui.components.CustomSlideTransition
import ui.deals.DealsView
import ui.menu.TabBarScreen
import ui.menu.TabBarScreen2

class HomeScreen : Screen {
    @OptIn(InternalVoyagerApi::class)
    @Composable
    override fun Content() {
        HomeView { }
    }

}

@Composable
fun HomeView(onNavigate: (String) -> Unit) {
    val navigator = LocalNavigator.currentOrThrow
    var showTabBarScreen by remember { mutableStateOf(false) }

    CustomSlideTransition(
        visible = showTabBarScreen,
        currentView = {
            Scaffold(
                bottomBar = {
                    Spacer(modifier = Modifier.height(70.dp))
                },
                floatingActionButton = {
                    Box(
                        modifier = Modifier.size(50.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.extraLarge
                            ).clip(MaterialTheme.shapes.extraLarge)
                            .clickable(role = Role.Image) {
                                showTabBarScreen = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            TablerIcons.Plus,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            ) { paddingValue ->
                DealsView(paddingValue) { route ->
                    onNavigate(route)
                }
            }
        }, slideView = {
            TabBarScreen {
                showTabBarScreen = false
            }
        })

}

object HomeScreenTab : Tab {
    @Composable
    override fun Content() {
        Navigator(HomeScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = rememberVectorPainter(TablerIcons.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

}