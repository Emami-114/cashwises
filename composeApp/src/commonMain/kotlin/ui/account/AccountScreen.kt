package ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import ui.account.auth.registration.RegistrationScreen
import ui.components.CustomBackgroundView
import ui.components.CustomButton
import ui.components.CustomDivider
import ui.components.CustomTopAppBar
import ui.search.SearchScreen

class AccountScreen : Screen {

    @Composable
    override fun Content() {

        Scaffold(topBar = {
            CustomTopAppBar(title = "Account")
        }) { paddingValue ->
            Box(modifier = Modifier.fillMaxSize()) {
                CustomBackgroundView()
                Column(
                    modifier = Modifier.padding(paddingValue),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val navigator: Navigator = LocalNavigator.currentOrThrow

                    CustomButton(title = "Login") {
                        navigator.push(RegistrationScreen())
                    }
                    CustomDivider()
                }
            }
        }
    }


}