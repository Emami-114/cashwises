package ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.CategoriesModel
import domain.model.DealModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.components.CustomBackgroundView
import ui.components.ProductRow
import ui.deals.components.CategoryItemView
import useCase.CategoryUseCase
import useCase.DealsUseCase


@OptIn(InternalResourceApi::class, ExperimentalResourceApi::class)
@Composable
fun SearchView() {
    var search by remember { mutableStateOf("") }
    val viewModel: SearchScreenViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            CustomSearchTopAppBar(
                searchText = uiState.searchQuery,
                onSearchTextChange = { searchText ->
                    viewModel.doChangeSearchText(searchText)
                    if (searchText.count() > 3) {
                        viewModel.doSearch()
                    }
                }, backButtonAction = {
//                        navigator.pop()
                }
            )
        },
    ) { paddingValues ->
        BoxWithConstraints {
            val scope = this
            val maxWidth = scope.maxWidth
            val column =
                if (maxWidth > 1150.dp) 5
                else if (maxWidth > 900.dp && maxWidth < 1150.dp) 4
                else if (maxWidth > 700.dp && maxWidth < 900.dp) 3
                else 3
            CustomBackgroundView()
            Column {
                Spacer(modifier = Modifier.height(15.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(column),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 5.dp)
                ) {
                    when {
                        uiState.deals != null && uiState.searchQuery.isEmpty().not() -> {
                            if (uiState.deals != null) {
                                items(uiState.deals!!) { deals ->
                                    ProductRow(deals) {
                                    }
                                }
                            }
                        }

                        else -> {
                            uiState.categories?.categories?.let { categories ->
                                items(categories) { category ->
                                    CategoryItemView(
                                        modifier = Modifier.heightIn(max = 150.dp)
                                            .widthIn(max = 100.dp),
                                        categoryModel = category
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


class SearchScreenViewModel : ViewModel(), KoinComponent {
    private val dealUseCase: DealsUseCase by inject()
    private val categoryUseCase: CategoryUseCase by inject()
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, SearchState())

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(categories = categoryUseCase.getCategories())
            }

        }
    }

    fun doSearch() = viewModelScope.launch {
        _state.update {
            it.copy(
                deals = dealUseCase.getDeals(query = _state.value.searchQuery)?.deals
            )
        }
    }

    fun doChangeSearchText(value: String) {
        _state.update {
            it.copy(searchQuery = value)
        }
    }
}

data class SearchState(
    val deals: List<DealModel>? = null,
    val categories: CategoriesModel? = null,
    val searchQuery: String = "",
    val page: Int = 1,
    val limit: Int = 20
)