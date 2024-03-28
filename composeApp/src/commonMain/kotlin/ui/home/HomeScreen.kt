package ui.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import ui.components.CustomTopAppBar
import ui.deals.DealsView

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        Scaffold(topBar = {
            CustomTopAppBar(title = "Home")
        }, bottomBar = {
            Spacer(modifier = Modifier.height(70.dp))
        }) {

            DealsView()

        }
    }
}