package ui.home.tags

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.arrow_up_right
import cashwises.composeapp.generated.resources.search
import domain.model.TagModel
import domain.repository.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_borderColor
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.components.CustomPopUp
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
    when {
        viewModel.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        viewModel.isError?.isNotEmpty() == true -> {
            CustomPopUp(present = true, message = viewModel.isError ?: "", cancelAction = {
                viewModel.isError = null
            })
        }

        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(bottom = 70.dp).padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(tags) { tag ->
                    Row(
                        modifier = Modifier.fillMaxWidth().height(35.dp).noRippleClickable {
                            onSelectedTag(tag.title)
                        }, horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(
                                painter = painterResource(Res.drawable.search),
                                contentDescription = null,
                                tint = cw_dark_grayText,
                                modifier = Modifier.size(26.dp)
                            )
                            Text(tag.title, fontSize = 15.sp, color = cw_dark_whiteText)
                        }
                        Icon(
                            painter = painterResource(Res.drawable.arrow_up_right),
                            contentDescription = null,
                            tint = cw_dark_grayText,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    HorizontalDivider(color = cw_dark_borderColor)
                }
            }
        }
    }
}

class TagsViewModel : ViewModel(), KoinComponent {
    private val useCase: TagsUseCase by inject()
    var isLoading: Boolean = false
    var isError: String? = null
    private val _state = MutableStateFlow(listOf<TagModel>())
    val state = _state.asStateFlow()

    fun getTags(query: String?) = viewModelScope.launch {
        try {
            useCase.getTags(query = query).collectLatest { status ->
                when (status) {
                    is Result.Loading -> isLoading = true
                    is Result.Success -> {
                        _state.update { status.data ?: emptyList() }
                        isLoading = false
                    }

                    is Result.Error -> {
                        isLoading = false
                        isError = getString(status.error?.message!!)
                    }
                }
            }
        } catch (e: Exception) {
            println("Error in TagsView ${e.message}")
        }
    }
}