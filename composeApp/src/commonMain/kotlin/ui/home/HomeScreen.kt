package ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Plus
import ui.AppScreen
import ui.components.CustomTopAppBar
import ui.deals.DealsView

@Composable
fun HomeView(onNavigate: (String) -> Unit) {
    val scrollState = rememberScrollState(initial = 10)
    Scaffold(modifier = Modifier,
        topBar = {
            CustomTopAppBar(title = "Home")
        },
        floatingActionButton = {
            Box(
                modifier = Modifier.padding(bottom = 100.dp).size(50.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.extraLarge
                    ).clip(MaterialTheme.shapes.extraLarge)
                    .clickable(role = Role.Image) {
                        onNavigate(AppScreen.CreateDeal.route)
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
        DealsView(
            modifier = Modifier.padding(top = paddingValue.calculateTopPadding())
        ) { route ->
            onNavigate(route)
        }
    }
}