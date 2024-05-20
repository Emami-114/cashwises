package ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.ChevronLeft
import data.model.DealsQuery
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.CategoriesModel
import domain.model.CategoryModel
import domain.model.DealModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.components.CustomBackgroundView
import ui.components.CustomDivider
import ui.components.CustomSearchView
import ui.components.CustomSlideTransition
import ui.components.ProductRow
import ui.components.customModiefier.noRippleClickable
import ui.deals.components.CategoryItemView
import useCase.CategoryUseCase
import useCase.DealsUseCase

@OptIn(InternalResourceApi::class, ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchView(argument: String? = null, onNavigate: (String) -> Unit) {
    val viewModel: SearchScreenViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    LaunchedEffect(true) {
        viewModel.doSearch()

    }
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val scope = this
        val maxWidth = scope.maxWidth
        val column = if (maxWidth > 1150.dp) 5
        else if (maxWidth > 900.dp && maxWidth < 1150.dp) 5
        else if (maxWidth > 700.dp && maxWidth < 900.dp) 4
        else 4
        CustomBackgroundView()

        LazyVerticalGrid(
            modifier = Modifier.padding(top = 10.dp).padding(horizontal = 5.dp),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.deals ?: listOf()) { deal ->
                ProductRow(dealModel = deal) {}
            }
        }
    }
}


@Composable
fun SearchScreen(modifier: Modifier = Modifier, onNavigate: (String) -> Unit) {
    val viewModel: SearchScreenViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    BoxWithConstraints {
        val scope = this
        val maxWidth = scope.maxWidth
        val column = if (maxWidth > 1150.dp) 5
        else if (maxWidth > 900.dp && maxWidth < 1150.dp) 5
        else if (maxWidth > 700.dp && maxWidth < 900.dp) 4
        else 4
        CustomBackgroundView()

        Column {
            SearchTopAppBar(modifier = Modifier, uiState = uiState, viewModel = viewModel)
            Spacer(modifier = Modifier.height(10.dp))
            CustomSlideTransition(
                visible = uiState.searchSelectedCategory != null || uiState.searchQuery != null,
                currentView = {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(column),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp)
                    ) {

                        uiState.categories?.categories?.let { categories ->
                            items(categories) { category ->
                                CategoryItemView(
                                    modifier = Modifier.heightIn(max = 120.dp)
                                        .widthIn(max = 100.dp),
                                    categoryModel = category
                                ) { selectedCategory ->
                                    viewModel.doChangeSearchCategory(selectedCategory)
                                }
                            }
                        }
                    }
                }, slideView = {
                    SearchView { }
                })
        }
        Spacer(modifier = Modifier.height(15.dp))


    }
}


class SearchScreenViewModel : ViewModel(), KoinComponent {
    private val dealUseCase: DealsUseCase by inject()
    private val categoryUseCase: CategoryUseCase by inject()
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow().stateIn(viewModelScope, SharingStarted.Lazily, SearchState())

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(categories = categoryUseCase.getCategories())
            }
        }
    }

    fun doSearch(
        query: DealsQuery = DealsQuery(
            searchQuery = _state.value.searchQuery,
            categories = listOf(_state.value.searchSelectedCategory?.id ?: "")
        )
    ) = viewModelScope.launch {
        _state.update {
            it.copy(
                deals = dealUseCase.getDeals(query = query)?.deals
            )
        }
    }

    fun doChangeSearchCategory(value: CategoryModel?) {
        _state.update {
            it.copy(searchSelectedCategory = value)
        }
    }

    fun doChangeSearchText(value: String?) {
        _state.update {
            it.copy(searchQuery = value)
        }
    }
}

data class SearchState(
    val deals: List<DealModel>? = null,
    val categories: CategoriesModel? = null,
    val searchQuery: String? = null,
    val page: Int = 1,
    val limit: Int = 20,
    val searchSelectedCategory: CategoryModel? = null,
)

@Composable
fun SearchTopAppBar(
    modifier: Modifier = Modifier,
    uiState: SearchState,
    viewModel: SearchScreenViewModel
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(cw_dark_background)
                .padding(top = 40.dp)
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (uiState.searchSelectedCategory != null || uiState.searchQuery != null) {
                Icon(
                    TablerIcons.ChevronLeft,
                    contentDescription = null,
                    tint = cw_dark_whiteText,
                    modifier = Modifier.size(30.dp).noRippleClickable {
                        viewModel.doChangeSearchCategory(null)
                        viewModel.doChangeSearchText(null)
                        focusManager.clearFocus()

                    }
                )
            }
            CustomSearchView(
                modifier = Modifier,
                value = uiState.searchQuery ?: "",
                onValueChange = { viewModel.doChangeSearchText(it) },
                onSearchClick = { viewModel.doChangeSearchText(it) },
                onFocused = { isFocused ->
                    if (isFocused) {
                        viewModel.doChangeSearchText("")
                    } else {
                        focusManager.clearFocus()
                    }
                }
            )
        }
        CustomDivider()
    }
}