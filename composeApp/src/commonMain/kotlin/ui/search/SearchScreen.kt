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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.chevron_left
import data.model.DealsQuery
import domain.model.CategoriesModel
import domain.model.CategoryModel
import domain.model.SmallDealModel
import domain.repository.Results
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.AppConstants
import ui.AppScreen
import ui.components.CustomBackgroundView
import ui.components.CustomDivider
import ui.components.CustomSearchView
import ui.components.CustomSlideTransition
import ui.components.CustomTopAppBar
import ui.components.ProductItem
import ui.components.customModiefier.noRippleClickable
import ui.deals.components.CategoryItemView
import ui.home.tags.TagsView
import useCase.CategoryUseCase
import useCase.DealsUseCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    categoryId: String? = null,
    tagArgument: String? = null,
    searchQuery: String? = null,
    title: String = "",
    onNavigate: (String) -> Unit
) {
    val viewModel: SearchScreenViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    LaunchedEffect(key1 = categoryId, key2 = tagArgument, key3 = searchQuery) {
        viewModel.doSearch(
            DealsQuery(
                categories = categoryId,
                filterTags = tagArgument,
                searchQuery = searchQuery
            )
        )
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

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            CustomTopAppBar(modifier = Modifier.fillMaxWidth(), title = title, backButtonAction = {
                onNavigate(AppConstants.BackClickRoute.route)
            })
            LazyVerticalGrid(
                modifier = Modifier.padding(top = 10.dp).padding(horizontal = 5.dp),
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(uiState.deals ?: listOf()) { deal ->
                    ProductItem(
                        dealModel = deal,
                        onNavigateToDetail = {
                            onNavigate.invoke(AppScreen.DealDetail.route + "/${deal.id}")
                        },
                        onNavigateToProvider = { url -> }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier, onNavigate: (String) -> Unit) {
    val viewModel: SearchScreenViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    var searchFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    BoxWithConstraints {
        val scope = this
        val maxWidth = scope.maxWidth
        val column = if (maxWidth > 1150.dp) 5
        else if (maxWidth > 900.dp && maxWidth < 1150.dp) 5
        else if (maxWidth > 700.dp && maxWidth < 900.dp) 4
        else 4
        CustomBackgroundView()

        Column {
            SearchTopAppBar(
                modifier = modifier,
                searchQuery = uiState.searchQuery ?: "",
                onQueryChange = { viewModel.doChangeSearchText(it) },
                focused = searchFocused,
                showBackButton = searchFocused && uiState.searchQuery?.isEmpty() ?: false,
                onFocusedChange = { isFocused ->
                    searchFocused = isFocused
                    if (isFocused) {
                        viewModel.doChangeSearchText("")
                    } else {
                        focusManager.clearFocus()
                    }
                },
                onNavigate = {
                    searchFocused = false
                    focusManager.clearFocus()
                },
                onSearch = { searchQuery ->
                    if (searchQuery.isEmpty().not()) {
                        onNavigate(AppScreen.SearchView.route + "?title=${searchQuery}&query=${searchQuery}")
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            CustomSlideTransition(
                visible = searchFocused,
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
                                    onNavigate(AppScreen.SearchView.route + "?categoryId=${selectedCategory.id}&title=${selectedCategory.title}")
                                }
                            }
                        }
                    }
                },
                slideView = {
                    TagsView(uiState = uiState, onSelectedTag = { tag ->
                        onNavigate(AppScreen.SearchView.route + "?tag=${tag}&title=${tag}")
                        focusManager.clearFocus()
                    })
                }
            )

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
        getCategories()
    }

    private fun getCategories() {
        try {
            viewModelScope.launch {
                _state.update {
                    it.copy(categories = categoryUseCase.getCategories())
                }
            }
        } catch (e: Exception) {
            println("error get categories in search view ${e.message}")
        }
    }

    fun doSearch(
        query: DealsQuery
    ) = viewModelScope.launch {
        dealUseCase.getDeals(query = query).collectLatest { status ->
            when (status) {
                is Results.Loading -> {}
                is Results.Success -> {
                    _state.update {
                        it.copy(
                            deals = status.data?.deals
                        )
                    }
                }

                is Results.Error -> {
                }
            }
        }
    }

    fun doChangeSearchCategory(value: CategoryModel?) {
        _state.update {
            it.copy(searchSelectedCategory = value)
        }
    }

    fun doChangeTags(value: String?) {
        _state.update {
            it.copy(searchSelectedTag = value)
        }
    }

    fun doChangeSearchText(value: String?) {
        _state.update {
            it.copy(searchQuery = value)
        }
    }
}

data class SearchState(
    val deals: List<SmallDealModel>? = null,
    val categories: CategoriesModel? = null,
    val searchQuery: String? = null,
    val page: Int = 1,
    val limit: Int = 20,
    val searchSelectedCategory: CategoryModel? = null,
    val searchSelectedTag: String? = null,
)

@Composable
fun SearchTopAppBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    focused: Boolean,
    showBackButton: Boolean,
    onFocusedChange: (Boolean) -> Unit,
    onNavigate: () -> Unit,
    onSearch: (String) -> Unit
) {

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
            if (showBackButton) {
                Icon(
                    painter = painterResource(Res.drawable.chevron_left),
                    contentDescription = null,
                    tint = cw_dark_whiteText,
                    modifier = Modifier.size(30.dp).noRippleClickable {
                        onNavigate()
                    }
                )
            }
            CustomSearchView(
                modifier = Modifier,
                value = searchQuery,
                onValueChange = { onQueryChange(it) },
                onSearchClick = { onSearch(it) },
                onFocused = onFocusedChange
            )
        }
        CustomDivider()
    }
}