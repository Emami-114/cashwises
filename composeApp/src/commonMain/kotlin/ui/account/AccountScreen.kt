package ui.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.components.CustomBackgroundView
import ui.components.CustomTopAppBar
import ui.menu.MenuBarView

class AccountScreen : Screen {

    @Composable
    override fun Content() {
        var tabbarExpanded by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            tabbarExpanded = true
        }
        Scaffold(topBar = {
            CustomTopAppBar(title = "Account")
        }, bottomBar = {

        }) { paddingValue ->
            Box(modifier = Modifier.fillMaxSize()) {
                CustomBackgroundView()
                Column(
                    modifier = Modifier.padding(paddingValue),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val navigator: Navigator = LocalNavigator.currentOrThrow
                    Box(modifier = Modifier.fillMaxSize()) {
                        MenuBarView(onClick = {
                            tabbarExpanded = false
                        })
                    }

                }
            }
        }
    }


}