package ui.account

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.transitions.SlideTransition
import ui.account.auth.registration.RegistrationScreen
import ui.components.CustomBackgroundView
import ui.components.CustomButton
import ui.components.CustomDivider
import ui.components.CustomTopAppBar
import ui.components.customModiefier.customBorder
import ui.menu.components.TabbarView
import ui.search.SearchScreen

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
                        TabbarView(onClick = {
                            tabbarExpanded = false
                        })
                    }

                }
            }
        }
    }


}