package ui.home.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import compose.icons.TablerIcons
import compose.icons.tablericons.Trash
import data.repository.UserRepository
import domain.model.TagModel
import domain.model.UserRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_whiteText
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.components.CustomButton
import ui.components.CustomPopUp
import ui.components.CustomTextField
import ui.components.customModiefier.customBorder
import useCase.TagsUseCase

@Composable
fun CreateTagView(modifier: Modifier = Modifier) {
    val viewModel: CreateTagViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    val tags by viewModel.tags.collectAsState()

    Box() {
        when {
            uiState.errorString != null -> {
                CustomPopUp(
                    present = uiState.errorString != null,
                    message = uiState.errorString ?: ""
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 70.dp)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(tags) { tag ->
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .background(
                                    cw_dark_onBackground,
                                    shape = MaterialTheme.shapes.large
                                )
                                .customBorder()
                                .padding(15.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(tag.title, fontSize = 20.sp, color = cw_dark_whiteText)
                                if (UserRepository.INSTANCE.user?.role == UserRole.ADMIN) {
                                    Icon(
                                        TablerIcons.Trash,
                                        contentDescription = null,
                                        tint = cw_dark_whiteText,
                                        modifier = Modifier.clickable {
                                            viewModel.deleteTag(tag.id ?: "")
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(cw_dark_onBackground)
                .padding(10.dp)
        ) {
            CustomTextField(
                value = uiState.title,
                onValueChange = { viewModel.onTitleChange(it) },
                placeholder = "Title",
                errorText = uiState.titleError
            )
            CustomButton(
                modifier = Modifier.padding(10.dp).height(50.dp),
                title = "Create"
            ) {
                viewModel.createTag()
            }
        }
    }
}

class CreateTagViewModel : ViewModel(), KoinComponent {
    private val useCase: TagsUseCase by inject()
    private val _state = MutableStateFlow(CreateTagState())
    val state = _state.asStateFlow()
    private val _tags = MutableStateFlow(listOf<TagModel>())
    val tags = _tags.asStateFlow()

    init {
        getTags()
    }

    private fun getTags() = viewModelScope.launch {
        try {
            _tags.update { useCase.getTags(null) }
        } catch (e: Exception) {
            _state.update {
                it.copy(errorString = e.message)
            }
            delay(4000)
            _state.update { it.copy(errorString = null) }
        }
    }

    fun createTag() = viewModelScope.launch {
        when {
            _state.value.title.isEmpty() || _state.value.title.isBlank() -> {
                _state.update {
                    it.copy(titleError = "Title Required")
                }
            }

            else -> {
                _state.value = _state.value.copy(isLoading = true)
                val tagModel = TagModel(title = _state.value.title)
                try {
                    useCase.createTag(tagModel) {
                        _state.update { CreateTagState() }
                        getTags()
                    }
                } catch (e: Exception) {
                    _state.update {
                        it.copy(errorString = e.message)
                    }
                    delay(4000)
                    _state.value = CreateTagState()
                }
            }
        }
    }

    fun deleteTag(id: String) = viewModelScope.launch {
        try {
            useCase.deleteTag(id) {
                _state.value = CreateTagState()
                getTags()
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(errorString = e.message)
            }
            delay(4000)
            _state.update { it.copy(errorString = null) }
        }
    }

    fun onTitleChange(value: String) {
        _state.update {
            it.copy(
                title = value,
                titleError = null
            )
        }
    }
}

data class CreateTagState(
    val title: String = "",
    val titleError: String? = null,
    val successfullyCreated: Boolean = false,
    val errorString: String? = null,
    val isLoading: Boolean = false
)


