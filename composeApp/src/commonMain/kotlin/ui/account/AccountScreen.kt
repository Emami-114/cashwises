package ui.account

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.account.auth.AuthView
import ui.components.CustomBackgroundView
import ui.components.CustomSlideTransition
import ui.components.CustomTopAppBar
import ui.menu.MenuBarEnum
import ui.menu.MenuBarView


@Composable
fun AccountScreen() {
    var tabBarExpanded by remember { mutableStateOf(false) }
    var currentTab by remember { mutableStateOf<MenuBarEnum?>(null) }
    LaunchedEffect(Unit) {
        tabBarExpanded = true
    }

    CustomSlideTransition(visible = currentTab == MenuBarEnum.LOGIN,
        currentView = {
            Scaffold(topBar = {
                CustomTopAppBar(title = "Account")
            }) { paddingValue ->
                Box(modifier = Modifier.fillMaxSize()) {
                    CustomBackgroundView()
                    Column(
                        modifier = Modifier.padding(paddingValue),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            MenuBarView(onClick = { tab ->
                                tabBarExpanded = false
                                currentTab = tab
                            })
                        }

                    }
                }
            }
        },
        slideView = {
            AuthView { currentTab = null }
        })

//    CustomSlideTransition(currentTab == MenuBarEnum.LOGIN) {
//        AuthView { currentTab = null }
//    }


//    when (currentTab) {
//        MenuBarEnum.LOGIN -> {
//
//
//        }
//
//        MenuBarEnum.PROFILE -> {}
//        MenuBarEnum.SETTING -> {}
//        MenuBarEnum.IMPRINT -> {}
//        MenuBarEnum.PRIVACYPOLICY -> {}
//        MenuBarEnum.LOGOUT -> {}
//        null -> {
//            Scaffold(topBar = {
//                CustomTopAppBar(title = "Account")
//            }) { paddingValue ->
//                Box(modifier = Modifier.fillMaxSize()) {
//                    CustomBackgroundView()
//                    Column(
//                        modifier = Modifier.padding(paddingValue),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Box(modifier = Modifier.fillMaxSize()) {
//                            MenuBarView(onClick = { tab ->
//                                tabbarExpanded = false
//                                currentTab = tab
//                            })
//                        }
//
//                    }
//                }
//            }
//        }
//    }

}