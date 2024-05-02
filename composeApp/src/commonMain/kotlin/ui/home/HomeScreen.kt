package ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
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
import ui.deals.DealsView
import ui.navigation.AppScreen


@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier.size(50.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.extraLarge
                    ).clip(MaterialTheme.shapes.extraLarge)
                    .clickable(role = Role.Image) {
                        onNavigate(AppScreen.Create.route)
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
        DealsView(paddingValue) { route ->
            onNavigate(route)

        }
    }
}