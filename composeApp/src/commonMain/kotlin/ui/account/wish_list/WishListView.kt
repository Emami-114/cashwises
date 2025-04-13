package ui.account.wish_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.wish_list
import data.repository.UserRepository
import domain.model.DealDetailModel
import domain.model.DetailRoute
import domain.repository.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.components.CustomTopAppBar
import ui.customNavigate
import ui.deals.components.ProductItemRow
import useCase.DealsUseCase

@Composable
fun WishListView(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: WishListViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    LaunchedEffect(viewModel.userRepository.userMarkedDeals.value) {
        viewModel.getUserWishList()
    }
    Column {
        CustomTopAppBar(
            title = stringResource(Res.string.wish_list),
            backButtonAction = {
                navController.popBackStack()
            })
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState) { deal ->
                ProductItemRow(dealDetailModel = deal) {
                    deal.id?.let { dealId ->
                        navController.customNavigate(DetailRoute(dealId))
                    }
                }
            }
        }
    }
}

class WishListViewModel : ViewModel(), KoinComponent {
    private val dealsUseCase: DealsUseCase by inject()
    val userRepository = UserRepository.INSTANCE
    private val _state = MutableStateFlow<List<DealDetailModel>>(listOf())
    val state = _state.asStateFlow()
    var error = mutableStateOf<String?>(null)

    init {
    }

    fun getUserWishList() = viewModelScope.launch {
        try {
            _state.emit(listOf())
            for (dealId in userRepository.userMarkedDeals.value) {
                dealsUseCase.getSingleDeal(dealId).collectLatest { status ->
                    when (status) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            _state.update {
                                ((it + status.data!!))
                            }
                        }
                        is Result.Error -> {}
                    }
                }

            }
        } catch (e: Exception) {
            error.value = e.message
        }

    }
}
