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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.DealModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject
import ui.components.CustomBackgroundView
import ui.components.CustomSlideTransition
import ui.components.ProductRow
import ui.deals.ViewModel.DealsViewModel


@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DealsView(paddingValues: PaddingValues, onNavigate: (String) -> Unit) {
    val viewModel: DealsViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    var selectedDeal by remember { mutableStateOf<DealModel?>(null) }
    var showDetail by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.getDeals()
    }
    val rememberLazyGridState = rememberLazyGridState()
    val navigator: Navigator = LocalNavigator.currentOrThrow

    CustomSlideTransition(showDetail, currentView = {
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
//                .padding(paddingValues)
                    .padding(all = 5.dp)
            ) {
                items(uiState.deals) { deal ->
                    ProductRow(dealModel = deal, onClick = {
                        selectedDeal = deal
                        showDetail = true
                        viewModel.doChangeSelectedDeal(deal)
//                    navigator.push(DetailDealScreen())
                    })
                }
            }
        }
    }, slideView = {
        DetailDealScreen() { showDetail = false }
    })
}