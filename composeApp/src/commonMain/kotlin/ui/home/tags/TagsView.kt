package ui.home.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowForwardUp
import compose.icons.tablericons.Search
import data.model.DealsQuery
import domain.model.TagModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_borderColor
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_whiteText
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.components.CustomDivider
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable
import ui.search.SearchState
import useCase.TagsUseCase

@Composable
fun TagsView(modifier: Modifier = Modifier, uiState: SearchState, onSelectedTag: (String) -> Unit) {
    val viewModel: TagsViewModel = koinInject()
    val tags by viewModel.state.collectAsState()

    LaunchedEffect(uiState.searchQuery) {
        if (uiState.searchQuery.isNullOrBlank() || uiState.searchQuery.isEmpty()) {
            viewModel.getTags(null)
        } else {
            viewModel.getTags(uiState.searchQuery)
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(tags) { tag ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .noRippleClickable {
                        onSelectedTag(tag.title)
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(TablerIcons.Search, contentDescription = null, tint = cw_dark_grayText)
                    Text(tag.title, fontSize = 15.sp, color = cw_dark_whiteText)
                }
                Icon(
                    TablerIcons.ArrowForwardUp,
                    contentDescription = null,
                    tint = cw_dark_grayText
                )
            }
            HorizontalDivider(color = cw_dark_borderColor)
        }
    }
}

class TagsViewModel : ViewModel(), KoinComponent {
    private val useCase: TagsUseCase by inject()
    private val _state = MutableStateFlow(listOf<TagModel>())
    val state = _state.asStateFlow()

    fun getTags(query: String?) = viewModelScope.launch {
        try {
            _state.update { useCase.getTags(query = query) }
        } catch (e: Exception) {
            println("Error in TagsView ${e.message}")
        }
    }
}