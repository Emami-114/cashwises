package ui.deals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import domain.model.DetailRoute
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.components.CustomBackgroundView
import ui.components.CustomDivider
import ui.components.CustomPopUp
import ui.customNavigate
import ui.deals.components.ProductGridItem
import ui.deals.ViewModel.DealEvent
import ui.deals.ViewModel.DealsState
import ui.deals.ViewModel.DealsViewModel
import ui.deals.components.ProductItem

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DealsView(
    modifier: Modifier = Modifier,
    viewModel: DealsViewModel,
    isExpanded: Boolean,
    uiState: DealsState,
    navController: NavHostController
) {
    val rememberLazyGridState = rememberLazyGridState()
    var isRefreshed by remember { mutableStateOf(false) }
    val pullRefresh = rememberPullToRefreshState()

    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
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
                if (isExpanded) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(uiState.deals) { deal ->
                            ProductItem(
                                dealModel = deal,
                                onNavigateToDetail = {
                                    navController.customNavigate(DetailRoute(deal.id))
                                },
                                onNavigateToProvider = {})
                            CustomDivider(height = 0.6.dp)
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(column),
                        state = rememberLazyGridState,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(7.dp),
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .fillMaxSize()
                    ) {
                        items(uiState.deals) { deal ->
                            ProductGridItem(
                                dealModel = deal,
                                onNavigateToDetail = {
                                    navController.customNavigate(DetailRoute(deal.id))
                                },
                                onNavigateToProvider = { url ->

                                }
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }
            }
        }
    }
}