package ui.account.wish_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.wish_list
import data.repository.UserRepository
import domain.model.DealModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.AppConstants
import ui.AppScreen
import ui.components.CustomTopAppBar
import ui.deals.components.ProductItemRow
import useCase.DealsUseCase

@Composable
fun WishListView(modifier: Modifier = Modifier, onNavigate: (String) -> Unit) {
    val viewModel: WishListViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()

    Column {
        CustomTopAppBar(
            title = stringResource(Res.string.wish_list),
            backButtonAction = {
                onNavigate(AppConstants.BackClickRoute.route)
            })
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState) { deal ->
                ProductItemRow(dealModel = deal) {
                    onNavigate(AppScreen.DealDetail.route + "/${deal.id}")
                }
            }
        }
    }
}

class WishListViewModel : ViewModel(), KoinComponent {
    private val dealsUseCase: DealsUseCase by inject()
    private val userRepository = UserRepository.INSTANCE
    private val _state = MutableStateFlow<List<DealModel>>(listOf())
    val state = _state.asStateFlow()
    var error = mutableStateOf<String?>(null)

    init {
        getUserWishList()
    }

    private fun getUserWishList() = viewModelScope.launch {
        try {
            for (dealId in userRepository.userMarkedDeals.value) {
                _state.update {
                    (it + dealsUseCase.getSingleDeal(dealId)!!)
                }
            }
        } catch (e: Exception) {
            error.value = e.message
        }

    }
}
