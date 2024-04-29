package ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Plus
import domain.model.DealModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.account.AccountScreen
import ui.components.CustomSlideTransition
import ui.components.CustomTopAppBar
import ui.deals.DealsView
import ui.deals.DetailDealScreen
import ui.menu.BottomBarView
import ui.menu.BottomBarViewEnum
import ui.menu.TabBarScreen
import ui.notification.NotificationView
import ui.search.SearchView

@Composable
fun HomeScreen() {
    var currentTab by remember { mutableStateOf<BottomBarViewEnum?>(BottomBarViewEnum.HOME) }

    Scaffold(bottomBar = {
        if (BottomBarViewEnum.values().contains(currentTab))
        BottomBarView(currentTab ?: BottomBarViewEnum.HOME) {
            currentTab = it
        }
    }) {
        when (currentTab) {
            BottomBarViewEnum.HOME -> HomeView() { currentTab = null }
            BottomBarViewEnum.SEARCH -> SearchView()
            BottomBarViewEnum.NOTIFICATION -> NotificationView()
            BottomBarViewEnum.ACCOUNT -> AccountScreen()
            null -> TabBarScreen(onBack = { currentTab = BottomBarViewEnum.HOME })
        }
    }
}


@Composable
fun HomeView(onAction: () -> Unit) {
    var showDetail by remember { mutableStateOf(false) }
    var selectedDeal by remember { mutableStateOf<DealModel?>(null) }

    CustomSlideTransition(
        visible = showDetail,
        currentView = {
            Scaffold(
                topBar = {
                    CustomTopAppBar(title = "Home")
                },
                bottomBar = {
                    Spacer(Modifier.height(80.dp))
                },
                floatingActionButton = {
                    Box(
                        modifier = Modifier.size(50.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.extraLarge
                            ).clip(MaterialTheme.shapes.extraLarge)
                            .clickable(role = Role.Image) {
                                onAction()
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
                DealsView(paddingValue) {
                    selectedDeal = it
                    showDetail = true
                }
            }
        },
        slideView = {
            selectedDeal?.let { deal ->
                DetailDealScreen(dealModel = deal) {
                    showDetail = false
                    selectedDeal = null
                }
            }
        }
    )
}
