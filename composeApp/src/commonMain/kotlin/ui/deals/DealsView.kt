package ui.deals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject
import ui.components.CustomBackgroundView
import ui.components.ProductRow
import ui.deals.ViewModel.DealsViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DealsView(paddingValues: PaddingValues) {
    val viewModel: DealsViewModel = koinInject()
    val navigator = LocalNavigator.current
    val uiState by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getDeals()
    }
    val rememberLazyGridState = rememberLazyGridState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        CustomBackgroundView()
        val scope = this
        val maxWidth = scope.maxWidth
        val column =
            if (maxWidth > 1150.dp) 5
            else if (maxWidth > 900.dp && maxWidth < 1150.dp) 4
            else if (maxWidth > 700.dp && maxWidth < 900.dp) 3
            else 2

        LazyVerticalGrid(
            columns = GridCells.Fixed(column),
            state = rememberLazyGridState,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .padding(paddingValues)
                .padding(all = 5.dp)
        ) {
            items(uiState.deals) { deal ->
                ProductRow(deal, onClick = {
                    navigator?.push(DetailDealScreen(dealModel = deal))
                })
            }
        }
    }
}