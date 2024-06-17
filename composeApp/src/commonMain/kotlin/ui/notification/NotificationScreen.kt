package ui.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.ApiConfig
import data.repository.UserRepository
import domain.model.TagModel
import domain.repository.Results
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.getString
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.account.auth.verification.VerificationView
import ui.components.CustomBackgroundView
import ui.components.CustomMultiSelection
import ui.components.CustomPopUp
import ui.components.CustomSelectionView
import ui.components.CustomTextField
import ui.components.CustomToast
import ui.components.CustomTopAppBar
import ui.settings
import useCase.TagsUseCase
import utils.LocalPushNotification
import utils.PushNotificationModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationView(onNavigate: (String) -> Unit) {
    val viewModel: NotificationViewModel = koinInject()
    val tagsModels by viewModel.tagsModel.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        CustomTopAppBar(title = "")

        when {
            viewModel.errorString.value != null -> {
                CustomPopUp(
                    present = viewModel.errorString.value != null,
                    message = viewModel.errorString.value ?: "",
                    cancelAction = {
                        viewModel.errorString.value = null
                    }
                )
            }

            viewModel.isLoading.value -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(all = 10.dp)
                ) {
                    items(tagsModels) { tag ->
                        CustomSelectionView(text = tag.title) {
                        }
                    }
                }
            }
        }
    }
}

class NotificationViewModel : ViewModel(), KoinComponent {
    var tagsModel = MutableStateFlow<List<TagModel>>(listOf())
    private val tagsUseCase: TagsUseCase by inject()
    var isLoading = mutableStateOf(false)
    var errorString = mutableStateOf<String?>(null)

    init {
        getTags()
    }

    private fun getTags() = viewModelScope.launch {
        tagsUseCase.getTags(null).collectLatest { status ->
            when (status) {
                is Results.Loading -> {
                    isLoading.value = true
                }

                is Results.Success -> {
                    tagsModel.emit(status.data ?: listOf())
                    errorString.value = null
                    isLoading.value = false
                }

                is Results.Error -> {
                    errorString.value = getString(status.error?.message!!)
                }
            }
        }
    }
}