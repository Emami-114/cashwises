package ui.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.components.CustomBackgroundView
import ui.menu.MenuBarEnum
import ui.menu.MenuBarView
import ui.navigation.AppScreen


@Composable
fun AccountScreen(onNavigate: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        CustomBackgroundView()
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MenuBarView(onClick = { tab ->
                    if (tab == MenuBarEnum.LOGIN) {
                        onNavigate(AppScreen.Login.route)
                    }
                })
            }

        }
    }

}