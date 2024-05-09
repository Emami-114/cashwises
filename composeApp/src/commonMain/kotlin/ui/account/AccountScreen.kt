package ui.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalClipboardManager
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import compose.icons.TablerIcons
import compose.icons.tablericons.User
import ui.account.auth.AuthView
import ui.components.CustomBackgroundView
import ui.components.CustomSlideTransition
import ui.menu.MenuBarEnum
import ui.menu.MenuBarView

class AccountScreen : Screen {
    @Composable
    override fun Content() {
        AccountView { }
    }

}

@Composable
fun AccountView(onNavigate: (String) -> Unit) {
    val navigator = LocalNavigator.currentOrThrow
    val clipBoard = LocalClipboardManager.current

    var currentScreen by remember { mutableStateOf<MenuBarEnum?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {
        CustomBackgroundView()
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                CustomSlideTransition(visible = currentScreen != null, currentView = {
                    MenuBarView(onClick = { tab ->
                        currentScreen = tab
                    })
                }, slideView = {
                    when (currentScreen) {
                        MenuBarEnum.LOGIN -> {
                            AuthView { currentScreen = null }
                        }

                        MenuBarEnum.PROFILE -> {

                        }
                        MenuBarEnum.SETTING -> TODO()
                        MenuBarEnum.IMPRINT -> TODO()
                        MenuBarEnum.PRIVACYPOLICY -> TODO()
                        MenuBarEnum.LOGOUT -> TODO()

                       else -> {}
                    }
                })

//                when (currentScreen) {
//                    MenuBarEnum.LOGIN -> {
//                        AuthView { currentScreen = null }
//                    }
//
//                    MenuBarEnum.PROFILE -> TODO()
//                    MenuBarEnum.SETTING -> TODO()
//                    MenuBarEnum.IMPRINT -> TODO()
//                    MenuBarEnum.PRIVACYPOLICY -> TODO()
//                    MenuBarEnum.LOGOUT -> TODO()
//
//                    null -> {
//                        MenuBarView(onClick = { tab ->
//                            currentScreen = tab
//                        })
//                    }
//                }

            }

        }
    }
}

object AccountScreenTab : Tab {
    @Composable
    override fun Content() {
        Navigator(AccountScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Account"
            val icon = rememberVectorPainter(TablerIcons.User)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }

}