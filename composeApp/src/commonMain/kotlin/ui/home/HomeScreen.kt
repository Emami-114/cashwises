package ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowBackUp
import compose.icons.tablericons.Plus
import org.company.app.theme.cw_dark_whiteText
import ui.AppScreen
import ui.deals.DealsView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(onNavigate: (String) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(modifier = Modifier.nestedScroll(
        connection = scrollBehavior.nestedScrollConnection,
    ),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("TOP APP BAR", color = cw_dark_whiteText) },
                navigationIcon = {
                    Icon(imageVector = TablerIcons.ArrowBackUp, contentDescription = null)
                },
                actions = { Icon(TablerIcons.Plus, contentDescription = null) },
                scrollBehavior = scrollBehavior
            )
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

        Box(modifier = Modifier.fillMaxSize().padding(top = paddingValue.calculateTopPadding())) {
            DealsView(paddingValue) { route ->
                onNavigate(route)
            }
        }
    }
}