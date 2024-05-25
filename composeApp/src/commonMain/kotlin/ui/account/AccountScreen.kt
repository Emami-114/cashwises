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
    Box(modifier = Modifier.fillMaxSize()) {
        CustomBackgroundView()
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MenuBarView(onNavigate = {
                    onNavigate(it)
                })
            }

        }
    }
}