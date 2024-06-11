package ui.deals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.DealModel
import domain.model.SmallDealModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject
import ui.AppScreen
import ui.components.CustomBackgroundView
import ui.components.CustomPopUp
import ui.components.ProductItem
import ui.deals.ViewModel.DealEvent
import ui.deals.ViewModel.DealsViewModel
import ui.deals.components.ProductItemRow


@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DealsView(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val viewModel: DealsViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    var showDetail by remember { mutableStateOf(false) }
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
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                CustomPopUp(present = true, message = uiState.error ?: "", cancelAction = {
                    viewModel.onEvent(DealEvent.OnSetDefaultState)
                })
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(column),
                    state = rememberLazyGridState,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                    modifier = modifier
                        .padding(all = 5.dp)
                ) {
                    items(uiState.deals) { deal ->
                        ProductItem(
                            dealModel = deal,
                            onClick = {
                                showDetail = true
                                onNavigate(AppScreen.DealDetail.route + "/${deal.id}")
                            })
                    }
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}