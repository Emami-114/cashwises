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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.TablerIcons
import compose.icons.tablericons.Plus
import ui.components.CustomTopAppBar
import ui.deals.DealsView
import ui.menu.BottomBarViewEnum
import ui.menu.TabBarScreen

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        var currentTab by remember { mutableStateOf(BottomBarViewEnum.HOME) }
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                CustomTopAppBar(title = "Home")
            },
            bottomBar = {
                Spacer(modifier = Modifier.height(90.dp))
            },
            floatingActionButton = {
                Box(
                    modifier = Modifier.size(50.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.extraLarge
                        ).clip(MaterialTheme.shapes.extraLarge)
                        .clickable(role = Role.Image) {
                            navigator.push(TabBarScreen())
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
            DealsView(paddingValue)

//            when (currentTab) {
//                BottomBarViewEnum.HOME -> DealsView()
//                BottomBarViewEnum.SEARCH -> SearchScreen()
//                BottomBarViewEnum.NOTIFICATION -> {}
//                BottomBarViewEnum.ACCOUNT -> AccountScreen()
//            }
        }
    }
}