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
import ui.account.auth.AuthView
import ui.components.CustomBackgroundView
import ui.components.CustomSlideTransition
import ui.menu.MenuBarEnum
import ui.menu.MenuBarView

@Composable
fun AccountView(onNavigate: (String) -> Unit) {
    var currentScreen by remember { mutableStateOf<MenuBarEnum?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        CustomBackgroundView()
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                CustomSlideTransition(visible = currentScreen != null, currentView = {
                    MenuBarView(onNavigate = {
                        onNavigate(it)
                    }, onClick = { tab ->
//                        currentScreen = tab
                    })
                }, slideView = {
                    when (currentScreen) {
                        MenuBarEnum.LOGIN -> {
                            AuthView {
                                currentScreen = null
                            }
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
            }

        }
    }
}